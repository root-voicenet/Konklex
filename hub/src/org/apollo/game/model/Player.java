package org.apollo.game.model;

/**
 * A remotely controlled player.
 * @author Steve
 */
public final class Player {

	/**
	 * The rights.
	 */
	private final int rights;

	/**
	 * Creates the player.
	 * @param rights The rigts.
	 */
	public Player(int rights) {
		this.rights = rights;
	}

	/**
	 * Gets the rights.
	 * @return The rights.
	 */
	public int getRights() {
		return rights;
	}

}
