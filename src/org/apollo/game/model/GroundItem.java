package org.apollo.game.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apollo.game.event.impl.GroundItemEvent;
import org.apollo.game.event.impl.PositionEvent;
import org.apollo.game.event.impl.RemoveGroundItemEvent;
import org.apollo.game.scheduling.impl.ProcessGroundItemsTask;

/**
 * An task for creating and destorying ground items.
 * @author Steve
 */
public class GroundItem {

	/** The Constant instance. */
	private static final GroundItem instance = new GroundItem();

	/**
	 * Get the instance.
	 * @return {@link GroundItem}'s
	 */
	public static GroundItem getInstance() {
		return instance;
	}

	/**
	 * The amount of pulses before a ground item turns global.
	 */
	public final int GLOBAL_PULSES = 250;

	/**
	 * The task that processes all registered ground items.
	 */
	private static ProcessGroundItemsTask processTask;

	/**
	 * The name of the player who controls this item.
	 */
	private String controllerName;

	/**
	 * The item that is on the floor.
	 */
	private Item item;

	/**
	 * The position of the item.
	 */
	private Position position;

	/**
	 * The amount of remaining pulses until this item disappears.
	 */
	private int pulses;

	/**
	 * The map of the list of ground items on a position.
	 */
	private final Map<Position, List<GroundItem>> groundItems = new HashMap<Position, List<GroundItem>>();

	/**
	 * Prevent instantation.
	 */
	private GroundItem() {
	}

	/**
	 * Creates a private ground item.
	 * @param controllerName The controller of this item.
	 * @param item The item.
	 * @param position The position.
	 */
	public GroundItem(String controllerName, Item item, Position position) {
		pulses = 350;
		this.controllerName = controllerName;
		this.item = item;
		this.position = position;
	}

	/**
	 * Create a ground item.
	 * @param p the p
	 * @param itemId the item id
	 * @param itemAmount the item amount
	 * @param pos the pos
	 */
	public void create(final Character p, int itemId, int itemAmount, Position pos) {
		Item item = new Item(itemId, itemAmount);
		boolean stackable = item.getDefinition().isStackable();
		if (stackable) {
			GroundItem groundItem = new GroundItem(p instanceof Player ? ((Player) p).getName() : "", item, pos);
			p.send(new PositionEvent(p.getPosition(), pos));
			p.send(new GroundItemEvent(itemId, itemAmount));
			updateMap(groundItem, item);
		} else {
			for (int i = 0; i < itemAmount; i++) {
				GroundItem groundItem = new GroundItem(p instanceof Player ? ((Player) p).getName() : "", new Item(
						itemId, 1), pos);
				p.send(new PositionEvent(p.getPosition(), pos));
				p.send(new GroundItemEvent(itemId, 1));
				updateMap(groundItem, new Item(itemId, 1));
			}
		}
	}

	/**
	 * Decreases the pulses.
	 */
	public void decreasePulses() {
		pulses--;
	}

	/**
	 * Deletes and unregisters a ground item.
	 * @param groundItem The ground item to unregister.
	 */
	public void delete(GroundItem groundItem) {
		getItems().get(groundItem.getPosition()).remove(groundItem);
		if (getItems().get(groundItem.getPosition()).isEmpty()) {
			groundItems.remove(groundItem.getPosition());
		}
		for (Player player : World.getWorld().getPlayerRepository()) {
			player.send(new PositionEvent(player.getPosition(), groundItem.getPosition()));
			player.send(new RemoveGroundItemEvent(groundItem.getItem().getId(), groundItem.getItem().getAmount()));
		}
	}

	/**
	 * Are we at the item.
	 * @param p the p
	 * @param item the item
	 * @return {@link Boolean}
	 */
	public boolean equals(Position p, GroundItem item) {
		if (p.getLocalX() == item.getPosition().getLocalX()) {
			if (p.getLocalY() == item.getPosition().getLocalY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the item at the specified position.
	 * @param p the p
	 * @return {@link GroundItem}
	 */
	public GroundItem get(Position p) {
		int findx = p.getX() - 8 * p.getTopLeftRegionX();
		int findy = p.getY() - 8 * p.getTopLeftRegionY();
		Collection<List<GroundItem>> collection = getItems().values();
		Iterator<List<GroundItem>> iterator = collection.iterator();
		while (iterator.hasNext()) {
			List<GroundItem> list = iterator.next();
			if (list == null) {
				return null;
			}
			for (int i = 0; i < list.size(); i++) {
				GroundItem g = list.get(i);
				int lookx = g.getPosition().getX() - 8 * g.getPosition().getTopLeftRegionX();
				int looky = g.getPosition().getY() - 8 * g.getPosition().getTopLeftRegionY();
				if (findx == lookx && findy == looky) {
					return g;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the controller's name.
	 * @return The controller's name.
	 */
	public String getControllerName() {
		return controllerName;
	}

	/**
	 * Gets the item.
	 * @return The item.
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Get the ground items.
	 * @return {@link GroundItem}'s
	 */
	public Map<Position, List<GroundItem>> getItems() {
		return groundItems;
	}

	/**
	 * Gets the position.
	 * @return The position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Gets the pulses.
	 * @return The pulses.
	 */
	public int getPulses() {
		return pulses;
	}

	/**
	 * Send player items.
	 * @param player the player
	 */
	public void login(Player player) {
		Collection<List<GroundItem>> collection = getItems().values();
		Iterator<List<GroundItem>> iterator = collection.iterator();
		while (iterator.hasNext()) {
			List<GroundItem> list = iterator.next();
			if (list == null) {
				return;
			}
			for (int i = 0; i < list.size(); i++) {
				GroundItem g = list.get(i);
				if (g == null) {
					continue;
				}
				if (g.controllerName.equals(player.getName())) {
					player.send(new PositionEvent(player.getPosition(), g.getPosition()));
					player.send(new GroundItemEvent(g.getItem().getId(), g.getItem().getAmount()));
				}
			}
		}
	}

	/**
	 * Pick up a item.
	 * @param p the p
	 * @param position the position
	 * @param itemId the item id
	 */
	public void pickup(final Player p, Position position, int itemId) {
		if (p.getInventory().freeSlots() <= 0) {
			p.sendMessage("Not enough inventory slots");
		} else {
			GroundItem item = get(position);
			if (item != null) {
				if (item.item.getId() == itemId) {
					if (equals(p.getPosition(), item)) {
						delete(item);
						p.getInventory().add(item.item);
					}
				}
			}
		}
	}

	/**
	 * Check if the process is running.
	 */
	public void processTaskCheck() {
		if (processTask == null) {
			processTask = new ProcessGroundItemsTask();
			World.getWorld().schedule(processTask);
		}
	}

	/**
	 * Update the map items.
	 * @param g the g
	 * @param item the item
	 */
	public void updateMap(GroundItem g, Item item) {
		if (groundItems.get(g.getPosition()) == null) {
			groundItems.put(g.getPosition(), new ArrayList<GroundItem>());
		}
		groundItems.get(g.getPosition()).add(g);
		processTaskCheck();
	}
}