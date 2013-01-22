package org.apollo.game.model.inter.melee;

import org.apollo.game.model.Character;

/**
 * An {@code listener} for the {@link Combat} system
 * @author Steve
 */
public abstract class CombatListener {
	
	/**
	 * Called upon a initiated attack.
	 * @param source The source.
	 * @param victim The victim.
	 * @return True if continuing default combat, false if not.
	 */
	public boolean initiatedCombat(Character source, Character victim) {
		return true;
	}
	
	/**
	 * Called upon initiating death.
	 * @param source The source.
	 * @param victim The victim.
	 * @return True if continuing default death, false if not.
	 */
	public boolean initiatedDeath(Character source, Character victim) {
		return true;
	}
	
	/**
	 * Called upon initiating a hit.
	 * @param source The source.
	 * @param victim The victim.
	 * @param type The type of hit.
	 * @param damage The damage to hit.
	 * @return True if continuing default hit, false if not.
	 */
	public boolean initiatedHit(Character source, Character victim, int type, int damage) {
		return true;
	}

}
