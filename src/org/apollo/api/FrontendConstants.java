package org.apollo.api;

/**
 * Contains api-related constants.
 * @author Graham
 */
public final class FrontendConstants {

	/**
	 * The delay between consecutive pulses, in milliseconds.
	 */
	public static final int PULSE_DELAY = 600;

	/**
	 * The maximum events per pulse per session.
	 */
	public static final int EVENTS_PER_PULSE = 20;

	/**
	 * Default private constructor to prevent instantiation by other classes.
	 */
	private FrontendConstants() {
	}
}
