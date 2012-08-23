package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;

/**
 * An {@link Method} which sets server configs.
 * 
 * @author Steve
 */
public final class ConfigMethod extends Method {

	/**
	 * The id.
	 */
	private final int id;

	/**
	 * The state.
	 */
	private final int state;

	/**
	 * Creates the config method.
	 * 
	 * @param id
	 *            The id.
	 * @param state
	 *            The state.
	 */
	public ConfigMethod(String key, int id, int state) {
		super(key);
		this.id = id;
		this.state = state;
	}

	/**
	 * Gets the id.
	 * 
	 * @return The id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the state.
	 * 
	 * @return The state.
	 */
	public int getState() {
		return state;
	}

}
