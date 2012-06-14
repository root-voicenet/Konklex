package org.apollo.game.minigame.event;

import org.apollo.game.model.Player;

/**
 * An event that is called when a player joins a minigame.
 * @author Steve
 */
public class JoinEvent {

    /**
     * The player.
     */
    private final Player player;

    /**
     * The team.
     */
    private final int team;

    /**
     * Creates the join event.
     * @param player The player.
     * @param team The team.
     */
    public JoinEvent(Player player, int team) {
	this.player = player;
	this.team = team;
    }

    /**
     * Gets the player.
     * @return The player.
     */
    public Player getPlayer() {
	return player;
    }

    /**
     * Gets the team.
     * @return The team.
     */
    public int getTeam() {
	return team;
    }

}
