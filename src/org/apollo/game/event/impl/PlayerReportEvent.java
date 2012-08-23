package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} that is sent when a player reports another player.
 * @author Steve
 */
public final class PlayerReportEvent extends Event {

	/**
	 * The player that was reported.
	 */
	private final long player;

	/**
	 * The rule that was broken.
	 */
	private final int rule;

	/**
	 * Creates the player report event.
	 * @param player The player that was reported.
	 * @param rule The rule that was broken.
	 */
	public PlayerReportEvent(long player, int rule) {
		this.player = player;
		this.rule = rule;
	}

	/**
	 * Gets the player that was reported.
	 * @return The player that was reported.
	 */
	public long getPlayer() {
		return player;
	}

	/**
	 * Gets the rule that was broken.
	 * @return The rule that was broken.
	 */
	public int getRule() {
		return rule;
	}
}
