package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} for damaging entitys.
 * @author Steve
 */
public class DamageEvent extends Event {

	/**
	 * The combat style.
	 * @author Steve
	 */
	public enum CombatStyle {
		/**
		 * Melee combat.
		 */
		MELEE(0),
		/**
		 * mage combat.
		 */
		MAGE(2),
		/**
		 * Range combat.
		 */
		RANGE(1),
		/**
		 * Blocking.
		 */
		BLOCK(3);

		/**
		 * The combat style.
		 */
		private final int type;

		/**
		 * Create a new combat style.
		 * @param type The combat style.
		 */
		private CombatStyle(int type) {
			this.type = type;
		}

		/**
		 * Gets the combat style.
		 * @return The combat style.
		 */
		public int getType() {
			return this.type;
		}
	}

	/**
	 * The damage done.
	 */
	private int damageDone;

	/**
	 * The hit type.
	 */
	private int hitType;

	/**
	 * The current hp.
	 */
	private int currentHp;

	/**
	 * The max hp.
	 */
	private int maxHp;

	/**
	 * Create a new damage event.
	 * @param damageDone the damage done
	 * @param currentHp the current hp
	 * @param maxHp the max hp
	 */
	public DamageEvent(int damageDone, int currentHp, int maxHp) {
		this.damageDone = damageDone;
		this.hitType = damageDone == 0 ? 0 : 1;
		this.currentHp = currentHp;
		this.maxHp = maxHp;
	}

	/**
	 * Get the damage done.
	 * @return The damage done.
	 */
	public int getDamageDone() {
		return damageDone;
	}

	/**
	 * Get the hit style.
	 * @return The hit style.
	 */
	public int getHitStyle() {
		return damageDone == 0 ? CombatStyle.BLOCK.getType() : CombatStyle.MELEE.getType();
	}

	/**
	 * Get the hit type.
	 * @return The hit type.
	 */
	public int getHitType() {
		return hitType;
	}

	/**
	 * Get the current hp.
	 * @return The current hp.
	 */
	public int getHp() {
		return currentHp;
	}

	/**
	 * Get the max hp.
	 * @return The max hp.
	 */
	public int getMaxHp() {
		return maxHp;
	}
}