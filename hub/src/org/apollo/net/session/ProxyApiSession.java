package org.apollo.net.session;

import org.apollo.api.method.Method;

/**
 * The proxified api session.
 * @author Steve
 */
public final class ProxyApiSession {

	/**
	 * The session.
	 */
	private final ApiSession session;

	/**
	 * Creates the proxified api session.
	 * @param session The session.
	 */
	public ProxyApiSession(ApiSession session) {
		this.session = session;
	}

	/**
	 * Sends a method.
	 * @param method The method to send.
	 */
	public <E extends Method> void send(E method) {
		session.dispatchMethod(method);
	}
}