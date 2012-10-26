package org.apollo.game.minigame;

import org.apollo.game.minigame.event.DeathEvent;
import org.apollo.game.minigame.event.JoinEvent;
import org.apollo.game.minigame.event.LeaveEvent;
import org.apollo.game.model.Character;

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
	 * Called when a player was killed.
	 * @param event The event that contains character variables.
	 */
	public void playerDied(DeathEvent event) {

	}

	/**
	 * Called when a character is disconnected.
	 * @param character The character that is being disconnected.
	 */
	public void playerDisconnected(Character character) {

	}

	/**
	 * Called when a player is removed.
	 * @param event The event that contains player variables.
	 */
	public void playerRemoved(LeaveEvent event) {

	}

}
