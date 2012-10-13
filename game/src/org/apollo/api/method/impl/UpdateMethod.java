package org.apollo.api.method.impl;

import org.apollo.api.method.Method;

/**
 * An {@link Method} which updates a world.
 * @author Steve
 */
public final class UpdateMethod extends Method {
	
	/**
	 * The time.
	 */
	private final int time;
	
	/**
	 * Creates the update method.
	 * @param time The time.
	 */
	public UpdateMethod(int time) {
		this.time = time;
	}
	
	public int getTime() {
		return time;
	}

}
