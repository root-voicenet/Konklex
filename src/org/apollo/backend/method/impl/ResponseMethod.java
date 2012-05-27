package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;

/**
 * An {@link Method} that sends a response message to the frontend.
 * @author Steve
 */
public final class ResponseMethod extends Method {

	/**
	 * The response message.
	 */
	private final String responseMessage;

	/**
	 * The method that was requested.
	 */
	private final String requestedMethod;

	/**
	 * The error flag.
	 */
	private final boolean error;

	/**
	 * Creates the response method.
	 * @param requestedMethod The method that was requested.
	 * @param responseMessage The response message.
	 * @param error True if error, false if not.
	 */
	public ResponseMethod(String requestedMethod, String responseMessage, boolean error) {
		this.requestedMethod = requestedMethod;
		this.responseMessage = responseMessage;
		this.error = error;
	}

	/**
	 * Gets the method that was requested.
	 * @return The method that was requested.
	 */
	public String getRequestedMethod() {
		return requestedMethod;
	}

	/**
	 * Gets the response message.
	 * @return The response message.
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * Gets the error flag.
	 * @return True if error, false if not.
	 */
	public boolean isError() {
		return error;
	}

}
