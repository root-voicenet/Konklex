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

}
