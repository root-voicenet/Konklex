package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.net.release.r317.ConfigEventEncoder;

/**
 * ConfigEvent for the {@link ConfigEventEncoder}.
 * @author Steve
 */
public class ConfigEvent extends Event {

	/**
	 * The config id.
	 */
	private final int id;

	/**
	 * The {@code config} state.
	 */
	private final int value;

	/**
	 * Create a new config event.
	 * @param id The config id.
	 * @param value The config state.
	 */
	public ConfigEvent(int id, int value) {
		this.id = id;
		this.value = value;
	}

	/**
	 * The config id.
	 * @return {@link Integer} The config id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * The config state.
	 * @return {@link Integer} The config state.
	 */
	public int getValue() {
		return value;
	}
}
