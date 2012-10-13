package org.apollo.game.model.inter.store;

import java.util.ArrayList;

import org.apollo.game.event.impl.UpdateItemsEvent;
import org.apollo.game.event.impl.UpdateSlottedItemsEvent;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Inventory.StackMode;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.SlottedItem;
import org.apollo.game.model.inter.store.impl.CoinsShopPaymentType;

/**
 * A utility that holds items into a store.
 * @author Steve
 */
public final class Shop {

	/**
	 * An enumeration of shop types.
	 * @author Steve
	 */
	public static enum ShopType {
		/**
		 * You can only buy if the inventory has the item.
		 */
		LIMITED_BUY_ONLY,
		/**
		 * You can buy and the inventory item never gets deleted.
		 */
		UNLIMITED_BUY_ONLY,
		/**
		 * You can only buy and sell if the inventory has the item.
		 */
		BUY_AND_SELL,
	}

	/**
	 * The shop name.
	 */
	private final String name;

	/**
	 * The shop payment type.
	 */
	private ShopPaymentType payment = new CoinsShopPaymentType();

	/**
	 * The shop type.
	 */
	private final ShopType type;

	/**
	 * Holds the shop items.
	 */
	private final Inventory items = new Inventory(40, StackMode.STACK_ALWAYS);

	/**
	 * The shop's viewers.
	 */
	private final ArrayList<Player> players = new ArrayList<Player>();

	/**
	 * Create a new shop.
	 * @param name The shop name.
	 */
	public Shop(String name) {
		this(name, ShopType.BUY_AND_SELL);
	}

	/**
	 * Create a new shop.
	 * @param name The shop name.
	 * @param type The shop type.
	 */
	public Shop(String name, ShopType type) {
		this.name = name;
		this.type = type;
		items.addListener(new ShopAmountChangedListener(this));
	}

	/**
	 * Adds a bulk item to the store.
	 * @param item The item to add to the store.
	 */
	protected void addItem(Item item) {
		items.add(item);
	}

	/**
	 * Adds a shop viewer to the list.
	 * @param player The player.
	 */
	protected void addViewer(Player player) {
		if (!players.contains(player))
			players.add(player);
	}

	/**
	 * Buys a item from the store.
	 * @param player The player.
	 * @param item The item to buy.
	 */
	public void buyItem(Player player, SlottedItem item) {
		if (player.getInventory().freeSlots() >= item.getItem().getAmount()) {
			if (payment.buyItem(player, item)) {
				player.getInventory().add(item.getItem());
				player.send(new UpdateItemsEvent(3823, player.getInventory().getItems()));
				if (!type.equals(ShopType.UNLIMITED_BUY_ONLY))
					items.remove(item.getItem());
			}
		} else
			player.getInventory().forceCapacityExceeded();
	}

	/**
	 * Gets the buy value of the item.
	 * @param item The item that is being checked.
	 * @return The value of the item.
	 */
	public int buyValue(int item) {
		return payment.buyValue(item);
	}

	/**
	 * Gets the shop's items.
	 * @return The shop's items.
	 */
	public Inventory getItems() {
		return items;
	}

	/**
	 * Gets the shop's name.
	 * @return The shop's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the shop payment type.
	 * @return The shop payment type.
	 */
	public ShopPaymentType getPayment() {
		return payment;
	}

	/**
	 * Refresh the shop for all viewers.
	 */
	public void refresh() {
		for (final Player player : players)
			player.send(new UpdateItemsEvent(3900, items.getItems()));
	}

	/**
	 * Refresh the shop for all viewers.
	 * @param si The slotted item to refresh.
	 */
	public void refresh(SlottedItem si) {
		for (final Player player : players)
			player.send(new UpdateSlottedItemsEvent(3900, si));
	}

	/**
	 * Removes a shop viewer from the list.
	 * @param player The player.
	 */
	protected void removeViewer(Player player) {
		if (players.contains(player))
			players.remove(player);
	}

	/**
	 * Sells a item to the store.
	 * @param player The player that is selling this item.
	 * @param slot The item slot.
	 * @param item The item.
	 */
	public void sellItem(Player player, Item item) {
		if (type.equals(ShopType.BUY_AND_SELL)) {
			if (player.getInventory().contains(item))
				if (payment.sellItem(player, item)) {
					player.getInventory().remove(item);
					player.send(new UpdateItemsEvent(3823, player.getInventory().getItems()));
					items.add(item);
				}
		} else
			player.sendMessage("You cannot sell items in this shop.");
	}

	/**
	 * Gets the sell value of the item.
	 * @param item The item that is being checked.
	 * @return The value of the item.
	 */
	public int sellValue(int item) {
		if (!type.equals(ShopType.BUY_AND_SELL))
			return -1;
		else
			return payment.sellValue(item);
	}

	/**
	 * Sets the shop payment type.
	 * @param payment The shop payment.
	 */
	public void setPayment(ShopPaymentType payment) {
		this.payment = payment;
	}
}
