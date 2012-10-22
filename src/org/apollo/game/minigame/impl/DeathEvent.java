package org.apollo.game.minigame.impl;

import org.apollo.game.model.Character;

/**
 * An event which is executed by a killing of a character.
 * @author Buroa
 */
public final class DeathEvent {

	/**
	 * The player.
	 */
	private final Character player;

	/**
	 * The source.
	 */
	private final Character source;

	/**
	 * The player team.
	 */
	private final int playerTeam;

	/**
	 * The source team.
	 */
	private final int sourceTeam;

	/**
	 * Creates the new death event.
	 * @param player The player.
	 * @param source The source.
	 * @param playerTeam The player team.
	 * @param sourceTeam The source team.
	 */
	public DeathEvent(Character player, Character source, int playerTeam, int sourceTeam) {
		this.player = player;
		this.source = source;
		this.playerTeam = playerTeam;
		this.sourceTeam = sourceTeam;
	}

	/**
	 * Gets the player.
	 * @return The player.
	 */
	public Character getPlayer() {
		return player;
	}

	/**
	 * Gets the player team.
	 * @return The player team.
	 */
	public int getPlayerTeam() {
		return playerTeam;
	}

	/**
	 * Gets the source.
	 * @return The source.
	 */
	public Character getSource() {
		return source;
	}

	/**
	 * Gets the source team.
	 * @return The source team.
	 */
	public int getSourceTeam() {
		return sourceTeam;
	}

}
