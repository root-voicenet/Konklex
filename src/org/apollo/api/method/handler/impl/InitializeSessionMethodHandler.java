package org.apollo.api.method.handler.impl;

import org.apollo.ServerContext;
import org.apollo.api.ProxyApiSession;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.InitializeSessionMethod;
import org.apollo.game.model.World;
import org.apollo.net.codec.api.ApiMethodDecoder;
import org.apollo.net.codec.api.ApiMethodEncoder;
import org.apollo.net.codec.api.ApiPacketDecoder;
import org.apollo.net.codec.api.ApiPacketEncoder;
import org.apollo.net.session.ApiSession;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipeline;

/**
 * An {@link MethodHandler} for the {@link InitializeSessionMethod}
 * @author Steve
 */
public final class InitializeSessionMethodHandler extends MethodHandler<InitializeSessionMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, InitializeSessionMethod method) {
		final ApiSession apiSession = (ApiSession) method.getApiSession();
		final Channel channel = apiSession._getChannel();
		final ChannelPipeline pipeline = channel.getPipeline();
		final ServerContext serverContext = World.getWorld().getContext();
		pipeline.addFirst("eventEncoder", new ApiMethodEncoder(serverContext.getRelease()));
		pipeline.addBefore("eventEncoder", "gameEncoder", new ApiPacketEncoder());
		pipeline.addBefore("handler", "gameDecoder", new ApiPacketDecoder(serverContext.getRelease()));
		pipeline.addAfter("gameDecoder", "eventDecoder", new ApiMethodDecoder(serverContext.getRelease()));
		pipeline.remove("apiDecoder");
		pipeline.remove("timeout");
		apiSession.sendPlayers();
		serverContext.set(method.getRequest());
	}

}
