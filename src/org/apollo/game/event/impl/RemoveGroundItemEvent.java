package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} that removes a item from a ground tile.
 * @author Steve
 */
public final class RemoveGroundItemEvent extends Event {

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
    public RemoveGroundItemEvent(int itemId, int itemAmount) {
	this.itemId = itemId;
	this.itemAmount = itemAmount;
    }

    /**
     * Return the item id.
     * @return The item id.
     */
    public int getId() {
	return itemId;
    }

    /**
     * Return the item amount.
     * @return The item amount.
     */
    public int getItemAmount() {
	return itemAmount;
    }
}