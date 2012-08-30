package org.apollo.game.model;

import org.apollo.game.model.Inventory;
import org.apollo.game.model.Item;

/**
 * Contains player-related bonuses.
 * @author Steve
 */
public final class PlayerBonuses {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * The equipment bonuses.
	 */
	private EquipmentBonuses bonuses;

	/**
	 * Creates player-related bonuses.
	 * @param player The player.
	 */
	public PlayerBonuses(Player player) {
		this.player = player;
	}

	/**
	 * Gets the equipment bonuses.
	 * @return The equipment bonuses.
	 */
	public EquipmentBonuses getBonuses() {
		return bonuses;
	}

	/**
	 * Refreshes the bonuses for this player.
	 */
	public void forceRefresh() {
		final Inventory equipment = player.getEquipment();
		EquipmentBonuses newBonuses = new EquipmentBonuses(new double[18]);

		for (int i = 0; i < equipment.capacity(); i++) {
			if (equipment.get(i) != null) {
				Item equiped = equipment.get(i);
				newBonuses = newBonuses.append(equiped.getBonuses());
			}
		}
		
		this.bonuses = newBonuses;
	}

}