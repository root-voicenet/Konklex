package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.Position;

/**
 * An {@link Event} which represents creating or destroying objects in game.
 * @author Steve
 */
public abstract class ObjectEvent extends MapEvent {

    /**
     * The object.
     */
    private final GameObject object;

    /**
     * Creates a new object event.
     * @param position The position of the game object.
     * @param object The object list.
     */
    public ObjectEvent(Position position, GameObject object) {
	super(position);
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
