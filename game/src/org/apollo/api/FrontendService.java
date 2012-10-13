package org.apollo.api;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apollo.Service;
import org.apollo.api.method.Method;
import org.apollo.api.method.handler.chain.MethodHandlerChainGroup;
import org.apollo.io.MethodHandlerChainParser;
import org.apollo.net.session.ApiSession;
import org.apollo.util.NamedThreadFactory;

/**
 * The {@link FrontendService} class schedules and manages the execution of the {@link FrontendPulseHandler} class.
 * @author Steve
 */
public final class FrontendService extends Service {

	/**
	 * The scheduled executor service.
	 */
	private final ScheduledExecutorService scheduledExecutor = Executors
			.newSingleThreadScheduledExecutor(new NamedThreadFactory("FrontendService"));

	/**
	 * A queue of current connected sessions.
	 */
	private final Queue<ApiSession> sessions = new ConcurrentLinkedQueue<ApiSession>();

	/**
	 * The {@link MethodHandlerChainGroup}.
	 */
	private MethodHandlerChainGroup chainGroup;

	/**
	 * Creates the frontend service.
	 * @throws Exception Exception on initialization.
	 */
	public FrontendService() throws Exception {
		init();
	}

	/**
	 * Adds a frontend session.
	 * @param session The session.
	 */
	public void addSession(ApiSession session) {
		sessions.add(session);
	}

	/**
	 * Gets the method handler chains.
	 * @return The method handler chains.
	 */
	public MethodHandlerChainGroup getMethodHandlerChains() {
		return chainGroup;
	}

	/**
	 * Initializes this service.
	 * @throws Exception Exception on methods.
	 */
	private void init() throws Exception {
		final InputStream is = new FileInputStream("data/methods.xml");
		try {
			final MethodHandlerChainParser chainGroupParser = new MethodHandlerChainParser(is);
			chainGroup = chainGroupParser.parse();
		}
		finally {
			is.close();
		}
	}

	/**
	 * Called every pulse.
	 */
	public void pulse() {
		synchronized (this) {
			for (final ApiSession session : sessions)
				if (session != null)
					session.handlePendingEvents(chainGroup);
				else
					removeSession(session);
		}
	}

	/**
	 * Removes a api session.
	 * @param session The session.
	 */
	public void removeSession(ApiSession session) {
		sessions.remove(session);
	}

	/**
	 * Sends all of the sessions the method.
	 * @param method The method to send.
	 */
	public <E extends Method> void sendAll(E method) {
		for (ApiSession session : sessions) {
			if (session != null) {
				session.dispatchMethod(method);
			}
		}
	}

	/**
	 * Starts the frontend service.
	 */
	@Override
	public void start() {
		scheduledExecutor.scheduleAtFixedRate(new FrontendPulseHandler(this), FrontendConstants.PULSE_DELAY,
				FrontendConstants.PULSE_DELAY, TimeUnit.MILLISECONDS);
	}
}