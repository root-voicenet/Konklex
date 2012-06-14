package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;

/**
 * An {@link Method} for item calls.
 * @author Steve
 */
public abstract class ItemMethod extends Method {

    /**
     * The player that is being called.
     */
    private final String player;

    /**
     * The item that is being called.
     */
    private final int item;

    /**
     * The amount of the item.
     */
    private final int amount;

    /**
     * Creates the item method.
     * @param player The player that is being called.
     * @param item The item that is being called.
     * @param amount The amount of the item.
     * @param key The key that is being sent.
     */
    public ItemMethod(String player, int item, int amount, String key) {
	super(key);
	this.player = player;
	this.item = item;
	this.amount = amount;
    }

    /**
     * Gets the amount of the item.
     * @return The amount of the item.
     */
    public int getAmount() {
	return amount;
    }

    /**
     * Gets the item that is being called.
     * @return The item that is being called.
     */
    public int getItem() {
	return item;
    }

    /**
     * Gets the player that is being called.
     * @return The player that is being called.
     */
    public String getPlayer() {
	return player;
    }

}
