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
		 * Hit.
		 */
		HIT(0),
		/**
		 * Miss.
		 */
		MISS(1),
		/**
		 * Poison.
		 */
		POISON(2),
		/**
		 * Disease.
		 */
		DISEASE(3);

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
		public int toInteger() {
			return type;
		}
	}

	/**
	 * The damage done.
	 */
	private final int damageDone;

	/**
	 * The hit type.
	 */
	private final int hitType;

	/**
	 * The current hp.
	 */
	private final int currentHp;

	/**
	 * The max hp.
	 */
	private final int maxHp;

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
	 * Create a new damage event.
	 * @param damageDone the damage done.
	 * @param currentHp the current hp.
	 * @param maxHp the max hp.
	 * @param hitType The hit type.
	 */
	public DamageEvent(int damageDone, int currentHp, int maxHp, int hitType) {
		this.damageDone = damageDone;
		this.currentHp = currentHp;
		this.maxHp = maxHp;
		this.hitType = hitType;
	}

	/**
	 * Get the damage done.
	 * @return The damage done.
	 */
	public int getDamageDone() {
		return damageDone;
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