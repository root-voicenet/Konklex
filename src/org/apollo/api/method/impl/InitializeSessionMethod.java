package org.apollo.api.method.impl;

import org.apollo.api.method.Method;
import org.apollo.net.codec.api.ApiRequest;
import org.apollo.net.session.Session;

/**
 * An {@link Method} which initializes a method.
 * @author Steve
 */
public final class InitializeSessionMethod extends Method {

	/**
	 * The sesion.
	 */
	private final Session session;
	
	/**
	 * The api request.
	 */
	private final ApiRequest request;

	/**
	 * Creates the initialize session.
	 * @param session The session.
	 * @param request The api request
	 */
	public InitializeSessionMethod(Session session, ApiRequest request) {
		this.session = session;
		this.request = request;
	}

	/**
	 * Gets the session.
	 * @return The session.
	 */
	public Session getApiSession() {
		return session;
	}
	
	/**
	 * Gets the api request.
	 * @return The api request.
	 */
	public ApiRequest getRequest() {
		return request;
	}
}
