package org.apollo.game.model;

import org.apollo.game.model.def.ObjectDefinition;

/**
 * Represents a single game object.
 * @author Graham Edgecombe
 */
public final class GameObject {

	/**
	 * The location.
	 */
	private final Position location;

	/**
	 * The definition.
	 */
	private final ObjectDefinition definition;

	/**
	 * The type.
	 */
	private final int type;

	/**
	 * The rotation.
	 */
	private final int rotation;

	/**
	 * The delete boolean.
	 */
	private final boolean delete;

	/**
	 * The replace boolean.
	 */
	private boolean replace;

	/**
	 * Creates the game object.
	 * @param definition The definition.
	 * @param location The location.
	 * @param type The type.
	 * @param rotation The rotation.
	 */
	public GameObject(ObjectDefinition definition, Position location, int type, int rotation) {
		this.definition = definition;
		this.location = location;
		this.type = type;
		this.rotation = rotation;
		this.delete = false;
	}
	
	/**
	 * Creates the game object.
	 * @param id The id.
	 * @param position The location.
	 * @param type The type.
	 * @param rotation The rotation.
	 */
	public GameObject(int id, Position position, int type, int rotation) {
		this(ObjectDefinition.forId(id), position, type, rotation);
	}
	
	/**
	 * Deletes the game object.
	 * @param id The id.
	 * @param position The location.
	 * @param type The type.
	 * @param rotation The rotation.
	 * @param delete Is deleting.
	 * @param replace Is replacing.
	 */
	public GameObject(int id, Position position, int type, int rotation, boolean delete, boolean replace) {
		this.definition = ObjectDefinition.forId(id);
		this.location = position;
		this.type = type;
		this.rotation = rotation;
		this.delete = delete;
		this.replace = replace;
	}

	/**
	 * Gets the definition.
	 * @return The definition.
	 */
	public ObjectDefinition getDefinition() {
		return definition;
	}

	/**
	 * Gets the location.
	 * @return The location.
	 */
	public Position getLocation() {
		return location;
	}

	/**
	 * Gets the rotation.
	 * @return The rotation.
	 */
	public int getRotation() {
		return rotation;
	}

	/**
	 * Gets the type.
	 * @return The type.
	 */
	public int getType() {
		return type;
	}

	/**
	 * The delete flag.
	 * @return True if deleting, false if otherwise.
	 */
	public boolean isDelete() {
		return delete;
	}
	
	/**
	 * The replace flag.
	 * @return True if replacing, false if otherwise.
	 */
	public boolean isReplace() {
		return replace;
	}

}
