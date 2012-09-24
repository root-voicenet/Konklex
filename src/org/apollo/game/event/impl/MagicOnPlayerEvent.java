package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} that is sent when the player attempts to cast magic onto another.
 * @author Steve
 */
public final class MagicOnPlayerEvent extends Event {

	/**
	 * The player that was clicked.
	 */
	private final int playerId;

	/**
	 * The spell id that was used.
	 */
	private final int spellId;

	/**
	 * Creates the magic on player event.
	 * @param playerId The player index that was clicked.
	 * @param spellId The spell id that was used.
	 */
	public MagicOnPlayerEvent(int playerId, int spellId) {
		this.playerId = playerId;
		this.spellId = spellId;
	}

	/**
	 * Gets the player id.
	 * @return The player id.
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * Gets the spell id.
	 * @return The spell id.
	 */
	public int getSpellId() {
		return spellId;
	}
}
