package org.apollo.game.model;

/**
 * A class representing characters attacking eachother.
 * @author Steve
 */
public final class MeleeSet {

	/**
	 * The character.
	 */
	@SuppressWarnings("unused")
	private final Character character;

	/**
	 * The attacker.
	 */
	private Character attacker;

	/**
	 * The special amount. TODO: Set this on logout and login.
	 */
	private int special = 100;

	/**
	 * Start a new melee class for the specified character.
	 * @param character The character.
	 */
	public MeleeSet(Character character) {
		this.character = character;
	}

	/**
	 * Gets the current attacker.
	 * @return The player's attacker.
	 */
	public Character getAttacker() {
		return attacker;
	}

	/**
	 * Gets the player's special.
	 * @return The player's special
	 */
	public int getSpecial() {
		return special;
	}

	/**
	 * Sets the current attacker.
	 * @param attacker The attacker
	 */
	public void setAttacker(Character attacker) {
		this.attacker = attacker;
	}

	/**
	 * Sets the player's special.
	 * @param special The special.
	 */
	public void setSpecial(int special) {
		this.special = special;
	}
}
