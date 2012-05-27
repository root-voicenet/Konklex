package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} that sends an item to the map tile.
 * @author Steve
 */
public final class GroundItemEvent extends Event {

    /**
     * The item id.
     */
    private final int itemId;

    /**
     * The item amount.
     */
    private final int itemAmount;

    /**
     * Create a new item.
     * @param itemId the item id
     * @param itemAmount the item amount
     */
    public GroundItemEvent(int itemId, int itemAmount) {
	this.itemId = itemId;
	this.itemAmount = itemAmount;
    }

    /**
     * Gets the item id.
     * @return The item id.
     */
    public int getId() {
	return itemId;
    }

    /**
     * Gets the item amount.
     * @return The item amount.
     */
    public int getItemAmount() {
	return itemAmount;
    }
}