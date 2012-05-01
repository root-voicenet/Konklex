package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * The Class GroundItemEvent.
 * @author Steve
 */
public final class GroundItemEvent extends Event {

	/**
	 * The item id.
	 */
	private int itemId;

	/**
	 * The item amount.
	 */
	private int itemAmount;

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
	 * Return the item id.
	 * @return {@link Integer}
	 */
	public int getId() {
		return itemId;
	}

	/**
	 * Return the item amount.
	 * @return {@link Integer}
	 */
	public int getItemAmount() {
		return itemAmount;
	}
}