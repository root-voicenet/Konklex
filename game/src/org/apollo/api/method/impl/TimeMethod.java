package org.apollo.api.method.impl;

import org.apollo.api.method.Method;

/**
 * An {@link Method} that sends the time to the login servers.
 * @author Steve
 */
public final class TimeMethod extends Method {

	/**
	 * The time.
	 */
	private final long time;

	/**
	 * Creates the time method.
	 * @param time The time.
	 */
	public TimeMethod(long time) {
		this.time = time;
	}

	/**
	 * Gets the time.
	 * @return The time.
	 */
	public long getTime() {
		return time;
	}

}
