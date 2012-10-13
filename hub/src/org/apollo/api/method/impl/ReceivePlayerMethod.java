package org.apollo.api.method.impl;

import org.apollo.api.method.Method;

/**
 * An {@link Method} that receives a player from a server.
 * @author Steve
 */
public final class ReceivePlayerMethod extends Method {

	/**
	 * The player.
	 */
	private final long player;

	/**
	 * The rights.
	 */
	private final int rights;

	/**
	 * The world.
	 */
	private final int world;

	/**
	 * The status.
	 */
	private final int status;

	/**
	 * Creates the send player method.
	 * @param player The player.
	 * @param rights The rights.
	 * @param status The status.
	 * @param world The world.
	 */
	public ReceivePlayerMethod(long player, int rights, int status, int world) {
		this.player = player;
		this.rights = rights;
		this.status = status;
		this.world = world;
	}

	/**
	 * Gets the player.
	 * @return The player.
	 */
	public long getPlayer() {
		return player;
	}

	/**
	 * Gets the rights.
	 * @return The rights.
	 */
	public int getRights() {
		return rights;
	}

	/**
	 * Gets the world.
	 * @return The world.
	 */
	public int getWorld() {
		return world;
	}

	/**
	 * Gets the status.
	 * @return The status.
	 */
	public int getStatus() {
		return status;
	}

}
