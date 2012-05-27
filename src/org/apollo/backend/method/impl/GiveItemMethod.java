package org.apollo.backend.method.impl;

/**
 * An {@link ItemMethod} that gives a player an item.
 * @author Steve
 */
public final class GiveItemMethod extends ItemMethod {

    /**
     * Creates the give item method.
     * @param player The player that this item is being given too.
     * @param item The item that is being given.
     * @param amount The amount of the item to be given.
     * @param key The key that is being sent.
     */
    public GiveItemMethod(String player, int item, int amount, String key) {
	super(player, item, amount, key);
    }

}