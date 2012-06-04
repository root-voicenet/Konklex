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
     * The remove flag.
     */
    private boolean remove = false;

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
     * Creates the game object.
     * @param definition The definition.
     * @param location The location.
     * @param type The type.
     * @param rotation The rotation.
     * @param remove True if removing, false if otherwise.
     */
    public GameObject(ObjectDefinition definition, Position location, int type, int rotation, boolean remove) {
	this(definition, location, type, rotation);
	this.remove = remove;
    }

    @Override
    public boolean equals(Object object) {
	if (object instanceof GameObject) {
	    final GameObject go = (GameObject) object;
	    if (go.getDefinition().getId() == definition.getId())
		if (go.getLocation().equals(location))
		    if (go.getRotation() == rotation)
			if (go.getType() == type)
			    if (go.isRemoving() == remove)
				return true;
	}
	return false;
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
     * Checks if this object is being removed.
     * @return True if removing, false if otherwise.
     */
    public boolean isRemoving() {
	return remove;
    }

}
