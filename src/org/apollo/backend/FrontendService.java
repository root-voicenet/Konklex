package org.apollo.backend;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apollo.Service;
import org.apollo.backend.codec.session.FrontendSession;
import org.apollo.backend.method.handler.chain.MethodHandlerChainGroup;
import org.apollo.game.GameConstants;
import org.apollo.io.MethodHandlerChainParser;
import org.apollo.util.NamedThreadFactory;

/**
 * The {@link FrontendService} class schedules and manages the execution of the
 * {@link FrontendPulseHandler} class.
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
    private final Queue<FrontendSession> sessions = new ConcurrentLinkedQueue<FrontendSession>();

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
    public void addSession(FrontendSession session) {
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
	} finally {
	    is.close();
	}
    }

    /**
     * Called every pulse.
     */
    public void pulse() {
	synchronized (this) {
	    for (final FrontendSession session : sessions)
		if (session != null)
		    session.handlePendingEvents(chainGroup);
		else
		    removeSession(session);
	}
    }

    /**
     * Removes a frontend session.
     * @param session The session.
     */
    public void removeSession(FrontendSession session) {
	sessions.remove(session);
    }

    /**
     * Starts the frontend service.
     */
    @Override
    public void start() {
	scheduledExecutor.scheduleAtFixedRate(new FrontendPulseHandler(this), GameConstants.PULSE_DELAY,
		GameConstants.PULSE_DELAY, TimeUnit.MILLISECONDS);
    }
}
