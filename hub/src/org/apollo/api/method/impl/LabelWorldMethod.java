package org.apollo.api.method.impl;

import org.apollo.api.method.Method;

/**
 * An {@link Method} which labels a server a world number.
 * @author Steve
 */
public final class LabelWorldMethod extends Method {

	/**
	 * The world.
	 */
	private final int world;

	/**
	 * Creates the label world method.
	 * @param world The world.
	 */
	public LabelWorldMethod(int world) {
		this.world = world;
	}

	/**
	 * Gets the world.
	 * @return The world.
	 */
	public int getWorld() {
		return world;
	}

}
