package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} that is sent when the player attempts to cast magic onto
 * another.
 * @author Steve
 */
public final class MagicOnPlayerEvent extends Event {

    /**
     * The player that was clicked.
     */
    private final int playerIndex;

    /**
     * The spell id that was used.
     */
    private final int spellId;

    /**
     * Creates the magic on player event.
     * @param playerIndex The player index that was clicked.
     * @param spellId The spell id that was used.
     */
    public MagicOnPlayerEvent(int playerIndex, int spellId) {
	this.playerIndex = playerIndex;
	this.spellId = spellId;
    }

    /**
     * Gets the player index.
     * @return The player index.
     */
    public int getPlayerIndex() {
	return playerIndex;
    }

    /**
     * Gets the spell id.
     * @return The spell id.
     */
    public int getSpellId() {
	return spellId;
    }
}
