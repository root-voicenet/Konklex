package org.apollo.net;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.ServerContext;
import org.apollo.api.method.impl.LabelWorldMethod;
import org.apollo.api.method.impl.ReceivePlayerMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.net.codec.api.ApiMethodDecoder;
import org.apollo.net.codec.api.ApiMethodEncoder;
import org.apollo.net.codec.api.ApiPacketDecoder;
import org.apollo.net.codec.api.ApiPacketEncoder;
import org.apollo.net.session.ApiSession;
import org.apollo.net.session.Session;
import org.apollo.util.NameUtil;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.UpstreamMessageEvent;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 * An implementation of {@link SimpleChannelUpstreamHandler} which handles incoming upstream events from Netty.
 * @author Graham
 */
@SuppressWarnings("unused")
public final class ApolloApiHandler extends IdleStateAwareChannelUpstreamHandler {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(ApolloApiHandler.class.getName());

	/**
	 * The server context.
	 */
	private final ServerContext serverContext;

	/**
	 * Creates the Apollo event handler.
	 * @param context The server context.
	 */
	public ApolloApiHandler(ServerContext context) {
		this.serverContext = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected
	 * (org.jboss.netty.channel.ChannelHandlerContext , org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		final Channel channel = ctx.getChannel();
		logger.info("Channel connected: " + channel);

		// send a ping
		ChannelBuffer buf = ChannelBuffers.buffer(1);
		buf.writeByte(1);
		channel.write(buf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelDisconnected (org.jboss.netty.channel.
	 * ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		final Channel channel = ctx.getChannel();
		logger.info("Channel disconnected: " + channel);
		serverContext.getServerChannelGroup().remove(channel);
		final Object attachment = ctx.getAttachment();
		if (attachment != null)
			((Session) attachment).destroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler# channelIdle(org.jboss.netty.channel.
	 * ChannelHandlerContext, org.jboss.netty.handler.timeout.IdleStateEvent)
	 */
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception {
		e.getChannel().close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(
	 * org.jboss.netty.channel.ChannelHandlerContext , org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		logger.log(Level.WARNING, "Exception occured for channel: " + e.getChannel() + ", closing...", e.getCause());
		ctx.getChannel().close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(
	 * org.jboss.netty.channel.ChannelHandlerContext , org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		if (ctx.getAttachment() == null) {
			final Object message = e.getMessage();
			if (message instanceof Integer) {
				final Channel channel = e.getChannel();
				final ChannelPipeline pipeline = channel.getPipeline();
				pipeline.addFirst("eventEncoder", new ApiMethodEncoder(serverContext.getFrontend()));
				pipeline.addBefore("eventEncoder", "gameEncoder", new ApiPacketEncoder());
				pipeline.addBefore("handler", "gameDecoder", new ApiPacketDecoder(serverContext.getFrontend()));
				pipeline.addAfter("gameDecoder", "eventDecoder", new ApiMethodDecoder(serverContext.getFrontend()));
				pipeline.remove("apiDecoder");
				serverContext.getServerChannelGroup().add(channel);
				final int world = serverContext.getServerChannelGroup().size();
				System.out.println(world);
				serverContext.getServerChannelGroup().getWorld(world).setTime((Integer) message);
				ctx.setAttachment(new ApiSession(channel, serverContext));
				channel.write(new LabelWorldMethod(world));
				for (Entry<Integer, World> entry : serverContext.getServerChannelGroup().getWorlds().entrySet()) {
					for (Entry<String, Player> es : entry.getValue().getPlayers().entrySet()) {
						String player = es.getKey();
						int rights = es.getValue().getRights();
						channel.write(new ReceivePlayerMethod(NameUtil.encodeBase37(player), rights, 1, entry.getKey()));
					}
				}
			}
		}
		else
			((Session) ctx.getAttachment()).messageReceived(e.getMessage());
	}
}
