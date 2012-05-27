package org.apollo.backend.method.impl;

/**
 * An {@link ItemMethod} that gives a player an item.
 * @author Steve
 */
public final class TakeItemMethod extends ItemMethod {

    /**
     * Creates the take item method.
     * @param player The player that this item is being taken from.
     * @param item The item that is being taken.
     * @param amount The amount of the item to be taken.
     * @param key The key that is being sent.
     */
    public TakeItemMethod(String player, int item, int amount, String key) {
	super(player, item, amount, key);
    }

}