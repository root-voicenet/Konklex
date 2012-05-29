package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.GroundItem;

/**
 * An {@link Event} that sends items to the map tile.
 * @author Steve
 */
public final class GroundItemEvent extends Event {

    /**
     * The ground item.
     */
    private final GroundItem item;

    /**
     * Create a new ground item event.
     * @param item The ground item.
     */
    public GroundItemEvent(GroundItem item) {
	this.item = item;
    }

    /**
     * Gets the ground item.
     * @return The ground item.
     */
    public GroundItem getGroundItem() {
	return item;
    }
}