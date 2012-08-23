package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;

/**
 * An {@link Method} that sends pushes game engine notifications.
 * 
 * @author Steve
 */
public final class NotificationMethod extends Method {

	/**
	 * The key.
	 */
	private final String key;

	/**
	 * The value.
	 */
	private final String value;

	/**
	 * Creates the new notification method.
	 * 
	 * @param key
	 *            The key.
	 * @param value
	 *            The value.
	 */
	public NotificationMethod(String key, String value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Gets the key.
	 * 
	 * @return The key.
	 */
	@Override
	public String getKey() {
		return key;
	}

	/**
	 * Gets the value.
	 * 
	 * @return The value.
	 */
	public String getValue() {
		return value;
	}

}