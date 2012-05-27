package org.apollo.game.event.impl;

/**
 * An {@link PlayerOptionEvent} that is sent when a player clicks the follow
 * option on another player.
 * @author Steve
 */
public final class PlayerFollowEvent extends PlayerOptionEvent {

    /**
     * Creates the player follow event.
     * @param otherId The other player id.
     */
    public PlayerFollowEvent(int otherId) {
	super(3, otherId);
    }
}
