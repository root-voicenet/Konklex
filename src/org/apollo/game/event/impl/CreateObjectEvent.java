package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} which represents creating or destroying objects in game.
 * @author Steve
 */
public abstract class CreateObjectEvent extends Event {

    /**
     * The object type.
     */
    private final int object;

    /**
     * Creates a new create object action event.
     * @param object The object id.
     */
    public CreateObjectEvent(int object) {
	this.object = object;
    }

    /**
     * Returns the object id.
     * @return {@link Integer} The object id.
     */
    public int getObject() {
	return object;
    }
}
