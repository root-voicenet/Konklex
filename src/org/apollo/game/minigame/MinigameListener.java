package org.apollo.game.minigame;

import org.apollo.game.model.Player;

/**
 * An {@link Minigame} listener that can control events from a minigame.
 * @author Steve
 */
public abstract class MinigameListener {

    /**
     * Called when a player is added.
     * @param player The player that was added.
     */
    public void playerAdded(Player player) {

    }

    /**
     * Called when a player is disconnected.
     * @param player The player that was disconnected.
     */
    public void playerDisconnected(Player player) {

    }

    /**
     * Called when a player is removed.
     * @param player The player that was removed.
     */
    public void playerRemoved(Player player) {

    }

}
