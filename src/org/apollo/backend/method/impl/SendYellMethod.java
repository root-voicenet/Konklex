package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;

/**
 * An {@link Method} for sending a global yell.
 * 
 * @author Steve
 */
public final class SendYellMethod extends Method {

	/**
	 * The message.
	 */
	private final String message;

	/**
	 * Creates the new send yell method.
	 * 
	 * @param message
	 *            The message.
	 * @param key
	 *            The key.
	 */
	public SendYellMethod(String message, String key) {
		super(key);
		this.message = message;
	}

	/**
	 * Gets the message.
	 * 
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

}
