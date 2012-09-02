package org.apollo.game.model;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.tools.EquipmentConstants;

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
	 * Returns the 317 bonus format.
	 * @param bonus The bonus.
	 * @return The 317 format bonus.
	 */
	private int bonusForId(int bonus) {
		int id = 0;
		switch(bonus) {
			case EquipmentBonuses.DEFENSE_SUMMONING:
			case EquipmentBonuses.STRENGTH_RANGE:
			case EquipmentBonuses.ABSORB_MAGIC:
			case EquipmentBonuses.ABSORB_MELEE:
			case EquipmentBonuses.ABSORB_RANGE:
				id = -1;
				break;
			case EquipmentBonuses.STRENGTH_MELEE:
				id = 10;
				break;
			case EquipmentBonuses.PRAYER:
				id = 1;
				break;
			default:
				id = bonus;
				break;
		}
		return id;
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
		writeBonus();
	}

	/**
	 * Gets the equipment bonuses.
	 * @return The equipment bonuses.
	 */
	public EquipmentBonuses getBonuses() {
		return bonuses;
	}

	/**
	 * Writes the bonuses.
	 */
	private void writeBonus() {
        int i = 0;
        String text = "";
        for (double bonus : bonuses) {
        	int bonusId = bonusForId(i);
        	if (bonusId != -1) {
	        	text = EquipmentConstants.BONUS_NAMES[bonusId] + ": " + Integer.toString((int) bonus);
	        	player.send(new SetInterfaceTextEvent(1675 + bonusId, text));
	        	i++;
        	}
        }
	}

}