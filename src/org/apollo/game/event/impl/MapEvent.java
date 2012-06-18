package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.Position;

/**
 * An {@link Event} for map events.
 * @author Steve
 */
public abstract class MapEvent extends Event {

    /**
     * The position of the map event.
     */
    private final Position position;

    /**
     * Creates a new map event.
     * @param position The position of the map event.
     */
    public MapEvent(Position position) {
	this.position = position;
    }

    /**
     * Gets the position of the map event.
     * @return The position of the map event.
     */
    public Position getPosition() {
	return position;
    }

}
