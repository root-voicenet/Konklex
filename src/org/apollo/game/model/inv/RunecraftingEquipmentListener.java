package org.apollo.game.model.inv;

import java.util.Enumeration;

import org.apollo.game.event.impl.ConfigEvent;
import org.apollo.game.model.EquipmentConstants;
import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

/**
 * An {@link InventoryListener} for the runecrafting skill.
 * @author Steve
 */
@SuppressWarnings("javadoc")
public final class RunecraftingEquipmentListener implements InventoryListener {

	/**
	 * An {@link Enumeration} of the tiara configurations.
	 * @author Steve
	 */
	public enum Tiara {
		AIR(2452, 1), MIND(2453, 2), WATER(2454, 4), EARTH(2455, 8), FIRE(2456, 16), BODY(2457, 32), COSMIC(2432, 64), CHAOS(
				2487, 128), NATURE(2486, 256), LAW(2485, 512), DEATH(2488, 1024);

		/**
		 * Gets the config value for the item.
		 * @param item The item.
		 * @return The config value for the item.
		 */
		public static int getValueForItem(int item) {
			for (Tiara tiara : values()) {
				if (tiara.getItem() == item)
					return tiara.getValue();
			}
			return -1;
		}

		/**
		 * The item.
		 */
		private final int item;

		/**
		 * The value.
		 */
		private final int value;

		/**
		 * Creates a new tiara.
		 * @param item The item.
		 * @param value The value.
		 */
		private Tiara(int item, int value) {
			this.item = item;
			this.value = value;
		}

		/**
		 * Gets the item.
		 * @return The item.
		 */
		public int getItem() {
			return item;
		}

		/**
		 * Gets the value.
		 * @return The value.
		 */
		public int getValue() {
			return value;
		}
	}

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Creates the runecrafting equipment listener.
	 * @param player The player.
	 */
	public RunecraftingEquipmentListener(Player player) {
		this.player = player;
	}

	@Override
	public void capacityExceeded(Inventory inventory) {

	}

	@Override
	public void itemsUpdated(Inventory inventory) {
		final int slot = EquipmentConstants.HAT;
		final Item item = inventory.get(slot);
		if (item != null) {
			itemUpdated(inventory, slot, item);
		}
	}

	@Override
	public void itemUpdated(Inventory inventory, int slot, Item item) {
		if (slot == EquipmentConstants.HAT) {
			final int value = Tiara.getValueForItem(item.getId());
			if (value != -1) {
				player.send(new ConfigEvent(491, value));
			}
		}
	}

}
