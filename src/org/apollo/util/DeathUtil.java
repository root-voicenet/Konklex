package org.apollo.util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;

/**
 * Find the top 3 items in the {@link Inventory}'s.
 * @author Steve
 */
public class DeathUtil {

	/**
	 * Compare the inventory values.
	 * @author Steve
	 */
	@SuppressWarnings("rawtypes")
	private static class ValueComparator implements Comparator {

		/** The base. */
		Map<Item, Integer> base;

		/**
		 * Compare the values.
		 * @param base The map.
		 */
		public ValueComparator(Map<Item, Integer> base) {
			this.base = base;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		@Override
		public int compare(Object a, Object b) {
			if (base.get(a) < base.get(b)) {
				return 1;
			} else if (base.get(a) == base.get(b)) {
				return -1;
			} else {
				return -1;
			}
		}
	}

	/**
	 * Find the top inventory items.
	 * @param player The player
	 * @param size The items to look for.
	 * @return {@link Inventory} The drop inventory.
	 */
	public static Inventory find(Player player, int size) {
		Inventory keep = new Inventory(size + 1);
		Map<Item, Integer> top = top(player);
		int count = 0;
		for (Item item : top.keySet()) {
			if (!(count >= size)) {
				keep.add(item);
				count++;
			}
		}
		return keep;
	}

	/**
	 * Get the top items, listed in order.
	 * @param player the player
	 * @return {@link Map} The map in price order.
	 */
	@SuppressWarnings("unchecked")
	private static Map<Item, Integer> top(Player player) {
		Map<Item, Integer> list = new HashMap<Item, Integer>();
		for (Item item : player.getInventory().getItems()) {
			if (item != null) {
				list.put(item, item.getDefinition().getValue());
			}
		}
		for (Item item : player.getEquipment().getItems()) {
			if (item != null) {
				list.put(item, item.getDefinition().getValue());
			}
		}
		ValueComparator bvc = new ValueComparator(list);
		TreeMap<Item, Integer> sorted_list = new TreeMap<Item, Integer>(bvc);
		sorted_list.putAll(list);
		list.clear();
		list.putAll(sorted_list);
		return list;
	}
}
