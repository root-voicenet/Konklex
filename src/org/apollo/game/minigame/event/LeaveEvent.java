package org.apollo.game.minigame.event;

import org.apollo.game.model.Player;

/**
 * An event that is called when a player leaves a team.
 * @author Steve
 */
public final class LeaveEvent extends JoinEvent {

    /**
     * Creates the leave event.
     * @param player The player.
     * @param team The team.
     */
    public LeaveEvent(Player player, int team) {
	super(player, team);
    }

}
