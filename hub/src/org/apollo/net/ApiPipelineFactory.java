package org.apollo.net;

import org.apollo.ServerContext;
import org.apollo.net.codec.api.ApiDecoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * A {@link ChannelPipelineFactory} for the API protocol.
 * @author Steve
 */
public final class ApiPipelineFactory implements ChannelPipelineFactory {

	/**
	 * The server event handler.
	 */
	private final ApolloApiHandler handler;

	/**
	 * The server context.
	 */
	private final ServerContext context;

	/**
	 * Creates the API pipeline factory.
	 * @param handler The file server event handler.
	 * @param context The server context.
	 */
	public ApiPipelineFactory(ApolloApiHandler handler, ServerContext context) {
		this.handler = handler;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		final ChannelPipeline pipeline = Channels.pipeline();
		// decoders
		pipeline.addLast("apiDecoder", new ApiDecoder(context));
		// handler
		pipeline.addLast("handler", handler);
		return pipeline;
	}
}
