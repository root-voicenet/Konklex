package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;

/**
 * An {@link Method} that sets a players privilege level.
 * @author Steve
 */
public final class SetPrivilegeLevelMethod extends Method {

    /**
     * The player to be set.
     */
    private final String player;

    /**
     * The privilege level.
     */
    private final int level;

    /**
     * Create a new set privilege level method.
     * @param player The player to be set.
     * @param level The privilege level.
     * @param key The key.
     */
    public SetPrivilegeLevelMethod(String player, int level, String key) {
	super(key);
	this.player = player;
	this.level = level;
    }

    /**
     * Gets the privilege level.
     * @return The privilege level.
     */
    public PrivilegeLevel getLevel() {
	return Player.PrivilegeLevel.valueOf(level);
    }

    /**
     * Gets the player to be set.
     * @return The player to be set.
     */
    public String getPlayer() {
	return player;
    }

}
