package org.apollo.game.model;

/**
 * NPCDefinition.java
 * @author The Wanderer
 */
public class NPCDefinition {

	/**
	 * Gets an npc definition by its id.
	 * @param id The id.
	 * @return The definition.
	 */
	public static NPCDefinition forId(int id) {
		return new NPCDefinition(id);
	}

	/**
	 * The id.
	 */
	private int id;

	/**
	 * Creates the definition.
	 * @param id The id.
	 */
	public NPCDefinition(int id) {
		this.id = id;
	}

	/**
	 * Gets the id.
	 * @return The id.
	 */
	public int getId() {
		return this.id;
	}
}