package org.apollo.backend.codec.session;

import org.apollo.backend.method.Method;

/**
 * An {@link FrontendSession} that restricts abilities.
 * @author Steve
 */
public final class FrontendChannel {

    /**
     * The frontend session.
     */
    private final FrontendSession session;

    /**
     * Creates a safe frontend session.
     * @param session The original frontend session.
     */
    public FrontendChannel(FrontendSession session) {
	this.session = session;
    }

    /**
     * Closes the session.
     */
    public void close() {
	session.close();
    }

    /**
     * Sends the method.
     * @param method The method.
     */
    public <E extends Method> void send(E method) {
	session.send(method);
    }

}
