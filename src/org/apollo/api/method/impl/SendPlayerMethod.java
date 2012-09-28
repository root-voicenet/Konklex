package org.apollo.api.method.impl;

import org.apollo.api.method.Method;

/**
 * An {@link Method} that sends a player to the hub.
 * @author Steve
 */
public final class SendPlayerMethod extends Method {
	
	/**
	 * The player.
	 */
	private final long player;
	
	/**
	 * The status.
	 */
	private final boolean online;

	/**
	 * The world.
	 */
	private int world;
	
	/**
	 * Creates the send player method.
	 * @param player The player.
	 * @param online The online flag.
	 */
	public SendPlayerMethod(long player, boolean online) {
		this.player = player;
		this.online = online;
	}
	
	/**
	 * Creates the receive player method.
	 * @param player The player.
	 * @param online The online flag.
	 * @param world The world.
	 */
	public SendPlayerMethod(long player, boolean online, int world) {
		this(player, online);
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
	 * Gets the online flag.
	 * @return The online flag.
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * Gets the world.
	 * @return The world.
	 */
	public int getWorld() {
		return world;
	}

}
