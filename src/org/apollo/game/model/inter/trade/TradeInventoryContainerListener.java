package org.apollo.game.model.inter.trade;

import org.apollo.game.event.impl.UpdateItemsEvent;
import org.apollo.game.event.impl.UpdateSlottedItemsEvent;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.SlottedItem;
import org.apollo.game.model.inv.InventoryAdapter;

/**
 * @author Steve
 */
public final class TradeInventoryContainerListener extends InventoryAdapter {

    private final Player player;

    /**
     * @param player
     */
    public TradeInventoryContainerListener(Player player) {
	this.player = player;
    }

    @Override
    public void itemsUpdated(Inventory container) {
	player.send(new UpdateItemsEvent(3322, container.getItems()));
    }

    @Override
    public void itemUpdated(Inventory container, int slot, Item item) {
	final SlottedItem si = new SlottedItem(slot, item);
	player.send(new UpdateSlottedItemsEvent(3322, si));
    }
}
