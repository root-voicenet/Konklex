package org.apollo.net;

import org.apollo.ServerContext;
import org.apollo.net.codec.telnet.TelnetHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

/**
 * A {@link ChannelPipelineFactory} for the HTTP protocol.
 * @author Graham
 */
public final class TelnetPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The maximum length of a request, in bytes.
	 */
	private static final int MAX_REQUEST_LENGTH = 8192;

	/**
	 * The server event handler.
	 */
	private final ApolloHandler handler;

	/**
	 * The server context.
	 */
	private final ServerContext context;

	/**
	 * Creates the TELNET pipeline factory.
	 * @param handler The file server event handler.
	 * @param context The server context.
	 */
	public TelnetPipelineFactory(ApolloHandler handler, ServerContext context) {
		this.handler = handler;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		final ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(MAX_REQUEST_LENGTH, Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast("handler", handler);
		pipeline.addLast("telnetHandler", new TelnetHandler(context));
		return pipeline;
	}
}
