package org.apollo.game.minigame;

import org.apollo.game.minigame.event.JoinEvent;
import org.apollo.game.minigame.event.LeaveEvent;
import org.apollo.game.model.Player;

/**
 * An {@link Minigame} listener that can control events from a minigame.
 * @author Steve
 */
public abstract class MinigameListener {

    /**
     * Called when a player is added.
     * @param event The event that contains player variables.
     */
    public void playerAdded(JoinEvent event) {

    }

    /**
     * Called when a player is disconnected.
     * @param player The player that was disconnected.
     */
    public void playerDisconnected(Player player) {

    }

    /**
     * Called when a player is removed.
     * @param event The event that contains player variables.
     */
    public void playerRemoved(LeaveEvent event) {

    }

}
