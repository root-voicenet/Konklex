package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.GameObject;

/**
 * An {@link Event} which represents creating or destroying objects in game.
 * @author Steve
 */
public final class ObjectEvent extends Event {

    /**
     * The object.
     */
    private final GameObject object;

    /**
     * Creates a new object event.
     * @param object The object list.
     */
    public ObjectEvent(GameObject object) {
	this.object = object;
    }

    /**
     * Gets the game object.
     * @return The game object.
     */
    public GameObject getObject() {
	return object;
    }

}
