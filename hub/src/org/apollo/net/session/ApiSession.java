package org.apollo.net.session;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.ServerContext;
import org.apollo.api.method.Method;
import org.apollo.api.method.handler.chain.MethodHandlerChain;
import org.apollo.api.method.handler.chain.MethodHandlerChainGroup;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * An {@link Session} for the api protocol.
 * @author Steve
 */
public final class ApiSession extends Session {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(ApiSession.class.getName());

	/**
	 * The server context.
	 */
	private final ServerContext context;

	/**
	 * Creates the api session.
	 * @param channel The channel.
	 */
	public ApiSession(Channel channel, ServerContext context) {
		super(channel);
		this.context = context;
	}

	@Override
	public void destroy() throws Exception {
	}

	@Override
	public void messageReceived(Object message) throws Exception {
		handleMethod((Method) message);
	}

	/**
	 * Encodes and dispatches the specified method.
	 * @param method The method.
	 */
	@SuppressWarnings("unused")
	public <E extends Method> void dispatchMethod(E method) {
		final Channel channel = getChannel();
		if (channel.isBound() && channel.isConnected() && channel.isOpen()) {
			final ChannelFuture future = channel.write(method);
			// if (method.getClass() == LogoutEvent.class) {
			// future.addListener(ChannelFutureListener.CLOSE);
			// }
		}
	}

	/**
	 * Handles pending events for this session.
	 * @param chainGroup The event chain group.
	 */
	@SuppressWarnings("unchecked")
	private <E extends Method> void handleMethod(E method) {
		MethodHandlerChainGroup chainGroup = context.getMethodHandlerChainGroup();
		Class<? extends Method> methodType = method.getClass();
		MethodHandlerChain<Method> chain = (MethodHandlerChain<Method>) chainGroup.getChain(methodType);
		while (chain == null && methodType != null) {
			methodType = (Class<? extends Method>) methodType.getSuperclass();
			if (methodType == Method.class) {
				methodType = null;
			}
			else {
				chain = (MethodHandlerChain<Method>) chainGroup.getChain(methodType);
			}
		}
		if (chain == null) {
			logger.warning("No chain for method: " + method.getClass().getName() + ".");
		}
		else {
			try {
				chain.handle(this, method);
			}
			catch (final Exception ex) {
				logger.log(Level.SEVERE, "Error handling method.", ex);
			}
		}
	}

}
