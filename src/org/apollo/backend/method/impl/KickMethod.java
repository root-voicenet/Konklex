package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;

/**
 * An {@link Method} for kicking a player from the game.
 * @author Steve
 */
public final class KickMethod extends Method {

    /**
     * The player.
     */
    private final String player;

    /**
     * Creates the new kick method.
     * @param player The player to kick.
     * @param key The key.
     */
    public KickMethod(String player, String key) {
	super(key);
	this.player = player;
    }

    /**
     * Gets the player.
     * @return The player.
     */
    public String getPlayer() {
	return player;
    }

}
