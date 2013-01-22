package org.apollo.net.session;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.ServerContext;
import org.apollo.api.FrontendService;
import org.apollo.api.method.Method;
import org.apollo.api.method.handler.chain.MethodHandlerChain;
import org.apollo.api.method.handler.chain.MethodHandlerChainGroup;
import org.apollo.api.method.impl.SendPlayerMethod;
import org.apollo.game.GameConstants;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * A api session.
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
	 * The queue of pending {@link Method}s.
	 */
	private final BlockingQueue<Method> methodQueue = new ArrayBlockingQueue<Method>(GameConstants.EVENTS_PER_PULSE);

	/**
	 * Creates a login session for the specified channel.
	 * @param channel The channel.
	 * @param context The server context.
	 */
	public ApiSession(Channel channel, ServerContext context) {
		super(channel);
		this.context = context;
	}

	/**
	 * Sends the players upon login.
	 */
	public void sendPlayers() {
		FrontendService service = context.getService(FrontendService.class);
		if (service != null) {
			for (Player player : World.getWorld().getPlayerRepository()) {
				service.sendAll(new SendPlayerMethod(player.getEncodedName(), true));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.session.Session#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		FrontendService service = context.getService(FrontendService.class);
		if (service != null) {
			service.removeSession(this);
		}
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
	public void handlePendingEvents(MethodHandlerChainGroup chainGroup) {
		for (Method method : methodQueue) {
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
		methodQueue.clear();
	}
	
	/**
	 * Gets the channel.
	 * @return The channel.
	 */
	public final Channel _getChannel() {
		return getChannel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.session.Session#messageReceived(java.lang.Object)
	 */
	@Override
	public void messageReceived(Object message) throws Exception {
		final Method method = (Method) message;
		if (methodQueue.size() >= GameConstants.EVENTS_PER_PULSE) {
			logger.warning("Too many methods in queue for api session, dropping...");
		}
		else {
			methodQueue.add(method);
		}
	}
}