package org.apollo.net;

import org.apollo.net.codec.api.ApiDecoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.Timer;

/**
 * A {@link ChannelPipelineFactory} for the API protocol.
 * @author Steve
 */
public final class ApiPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The server event handler.
	 */
	private final ApolloHandler handler;

	/**
	 * The timer used for idle checking.
	 */
	private final Timer timer;

	/**
	 * Creates the HTTP pipeline factory.
	 * @param handler The file server event handler.
	 * @param timer The timer used for idle checking.
	 */
	public ApiPipelineFactory(ApolloHandler handler, Timer timer) {
		this.handler = handler;
		this.timer = timer;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		final ChannelPipeline pipeline = Channels.pipeline();
		// handler
		pipeline.addLast("timeout", new IdleStateHandler(timer, NetworkConstants.IDLE_TIME, 0, 0));
		pipeline.addLast("handler", handler);
		pipeline.addBefore("handler", "apiDecoder", new ApiDecoder());
		return pipeline;
	}
}
