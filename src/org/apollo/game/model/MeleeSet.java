package org.apollo.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apollo.game.event.impl.ConfigEvent;
import org.apollo.game.event.impl.DamageEvent;
import org.apollo.game.event.impl.DamageEvent.CombatStyle;
import org.apollo.game.event.impl.SetInterfaceComponentEvent;
import org.apollo.game.model.inter.melee.CombatListener;
import org.apollo.game.sync.block.SynchronizationBlock;

/**
 * A class representing characters attacking eachother.
 * @author Steve
 */
public final class MeleeSet {

	/**
	 * The special flag.
	 */
	private boolean usingSpecial;

	/**
	 * The magic flag.
	 */
	private boolean usingMagic;

	/**
	 * The magic spell id.
	 */
	private int magicSpellId;

	/**
	 * The time since the last attack.
	 */
	private long lastAttack;

	/**
	 * The auto retaliating flag.
	 */
	private boolean autoRetaliating = true;

	/**
	 * The attack timer.
	 */
	private int attackTimer = 4;

	/**
	 * The attacking flag.
	 */
	private boolean attacking;

	/**
	 * The under attack flag.
	 */
	private boolean underAttack = false;

	/**
	 * The character.
	 */
	private final Character character;

	/**
	 * The interacting character.
	 */
	private Character interacter;

	/**
	 * The dying flag.
	 */
	private boolean dying;

	/**
	 * The special.
	 */
	private int special;

	/**
	 * The poison.
	 */
	private int poison;

	/**
	 * The last poison.
	 */
	private long lastPoison;

	/**
	 * The special bar id.
	 */
	private int specialBar;

	/**
	 * The attackable flag.
	 */
	private boolean attackable = true;
	
	/**
	 * The combat listeners.
	 */
	private final List<CombatListener> listeners = new ArrayList<CombatListener>(7);

	/**
	 * Start a new melee class for the specified character.
	 * @param character The character.
	 */
	public MeleeSet(Character character) {
		this.character = character;
	}
	
	/**
	 * Gets the combat listeners.
	 * @return The combat listeners.
	 */
	protected final List<CombatListener> getListeners() {
		return listeners;
	}
	
	/**
	 * Adds a combat listener.
	 * @param listener The listener to add.
	 */
	public void addListener(CombatListener listener) {
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	/**
	 * Removes a listener.
	 * @param listener The listener to remove.
	 */
	public void removeListener(CombatListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Damages the character.
	 * @param hit The damage to deal.
	 */
	public void damage(int hit) {
		DamageEvent damage = new DamageEvent(hit > character.getHealth() ? character.getHealth() : hit,
				character.getHealth(), character.getHealthMax());
		int health = character.getHealth() - damage.getDamageDone();
		character.setHealth(health);
		character.getBlockSet().add(SynchronizationBlock.createHitUpdateBlock(damage));
	}

	/**
	 * Damages the character.
	 * @param hit The damage to deal.
	 */
	public void damage2(int hit) {
		DamageEvent damage = new DamageEvent(hit > character.getHealth() ? character.getHealth() : hit,
				character.getHealth(), character.getHealthMax());
		int health = character.getHealth() - damage.getDamageDone();
		character.setHealth(health);
		character.getBlockSet().add(SynchronizationBlock.createSecondHitUpdateBlock(damage));
	}

	/**
	 * Diseases the character.
	 * @param hit The damage to deal.
	 */
	public void disease(int hit) {
		DamageEvent damage = new DamageEvent(hit > character.getHealth() ? character.getHealth() : hit,
				character.getHealth(), character.getHealthMax(), CombatStyle.DISEASE.toInteger());
		int health = character.getHealth() - damage.getDamageDone();
		character.setHealth(health);
		character.getBlockSet().add(SynchronizationBlock.createHitUpdateBlock(damage));
	}

	/**
	 * Gets the attack timer.
	 * @return The attack timer.
	 */
	public int getAttackTimer() {
		return attackTimer;
	}

	/**
	 * Gets the interacting character.
	 * @return The interacting character.
	 */
	public Character getInteractingCharacter() {
		return interacter;
	}

	/**
	 * Gets the last attack.
	 * @return The last attack.
	 */
	public long getLastAttack() {
		return lastAttack;
	}

	/**
	 * Gets the last poison.
	 * @return The last poison.
	 */
	public long getLastPoison() {
		return lastPoison;
	}

	/**
	 * Gets the magic spell id.
	 * @return The magic spell id.
	 */
	public int getMagicSpellId() {
		return magicSpellId;
	}

	/**
	 * Gets the poison.
	 * @return The poison.
	 */
	public int getPoison() {
		return poison;
	}

	/**
	 * Gets the special.
	 * @return The special.
	 */
	public int getSpecial() {
		return special;
	}

	/**
	 * Gets the special bar id.
	 * @return The special bar id.
	 */
	public int getSpecialBar() {
		return specialBar;
	}

	/**
	 * Gets the attackable flag.
	 * @return The attackable flag.
	 */
	public boolean isAttackable() {
		return attackable;
	}

	/**
	 * Gets the attacking flag.
	 * @return True if attacking, false if not.
	 */
	public boolean isAttacking() {
		return attacking;
	}

	/**
	 * Gets the auto retaliating flag.
	 * @return True if retaliating, false if not.
	 */
	public boolean isAutoRetaliating() {
		return autoRetaliating;
	}

	/**
	 * Gets the dying flag.
	 * @return True if dying, false if not.
	 */
	public boolean isDying() {
		return dying;
	}

	/**
	 * Gets the under attack flag.
	 * @return True if under attack, false if not.
	 */
	public boolean isUnderAttack() {
		return underAttack;
	}

	/**
	 * Gets the magic flag.
	 * @return True if using magic, false if not.
	 */
	public boolean isUsingMagic() {
		return usingMagic;
	}

	/**
	 * Gets the using special flag.
	 * @return True if using special, false if not.
	 */
	public boolean isUsingSpecial() {
		return usingSpecial;
	}

	/**
	 * Poisons the character.
	 * @param hit The damage to deal.
	 */
	public void poison(int hit) {
		DamageEvent damage = new DamageEvent(hit > character.getHealth() ? character.getHealth() : hit,
				character.getHealth(), character.getHealthMax(), CombatStyle.POISON.toInteger());
		int health = character.getHealth() - damage.getDamageDone();
		character.setHealth(health);
		character.getBlockSet().add(SynchronizationBlock.createHitUpdateBlock(damage));
		lastPoison = System.currentTimeMillis();
	}

	/**
	 * Sets the attackable flag.
	 * @param attackable The attackable flag.
	 */
	public void setAttackable(boolean attackable) {
		this.attackable = attackable;
	}

	/**
	 * Sets the attacking flag.
	 * @param attacking The attacking flag.
	 */
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	/**
	 * Sets the attack timer.
	 * @param attackTimer The attack timer.
	 */
	public void setAttackTimer(int attackTimer) {
		this.attackTimer = attackTimer;
	}

	/**
	 * Sets the auto retaliating flag.
	 * @param autoRetaliating The auto retaliating flag.
	 */
	public void setAutoRetaliating(boolean autoRetaliating) {
		this.autoRetaliating = autoRetaliating;
	}

	/**
	 * Sets the dying flag.
	 * @param dying The dying flag.
	 */
	public void setDying(boolean dying) {
		this.dying = dying;
	}

	/**
	 * Sets the interacting character.
	 * @param interacter The interacting character.
	 */
	public void setInteractingCharacter(Character interacter) {
		this.interacter = interacter;
	}

	/**
	 * Sets the last attack.
	 * @param lastAttack The last attack.
	 */
	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	/**
	 * Gets the last poison.
	 * @param lastPoison The last poison.
	 */
	public void setLastPoison(long lastPoison) {
		this.lastPoison = lastPoison;
	}

	/**
	 * Sets the magic spell id.
	 * @param magicSpellId The magic spell id.
	 */
	public void setMagicSpellId(int magicSpellId) {
		this.magicSpellId = magicSpellId;
	}

	/**
	 * Sets the poison.
	 * @param poison The poison.
	 */
	public void setPoison(int poison) {
		this.poison = poison;
	}

	/**
	 * Sets the special.
	 * @param special The special.
	 */
	public void setSpecial(int special) {
		this.special = special;
		character.send(new ConfigEvent(300, special));
	}

	/**
	 * Sets the special bar id.
	 * @param specialBar The special bar id.
	 */
	public void setSpecialBar(int specialBar) {
		this.specialBar = specialBar;
		character.send(new SetInterfaceComponentEvent(specialBar, true));
	}

	/**
	 * Sets the under attack flag.
	 * @param underAttack The under attack flag.
	 */
	public void setUnderAttack(boolean underAttack) {
		this.underAttack = underAttack;
	}

	/**
	 * Sets the using magic flag.
	 * @param usingMagic the using magic flag.
	 */
	public void setUsingMagic(boolean usingMagic) {
		this.usingMagic = usingMagic;
	}

	/**
	 * Sets the using special flag.
	 * @param usingSpecial the using special flag
	 */
	public void setUsingSpecial(boolean usingSpecial) {
		this.usingSpecial = usingSpecial;
		character.send(new ConfigEvent(301, usingSpecial ? 1 : 0));
	}
	
	/**
	 * Appends a hit to us.
	 * @param source The source.
	 * @param type The type.
	 * @param damage The damage.
	 * @return True if quit hit, false if not.
	 */
	public boolean appendHit(Character source, int type, int damage) {
		for (CombatListener listener : listeners) {
			boolean appendHit = listener.initiatedHit(source, character, type, damage);
			if (!appendHit) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Initiates the combat.
	 * @param source The source.
	 * @return True if quit hit, false if not.
	 */
	public boolean initiatedCombat(Character source) {
		for (CombatListener listener : listeners) {
			boolean initiateCombat = listener.initiatedCombat(source, character);
			if (!initiateCombat) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Initiates the death.
	 * @param source The source.
	 * @return True if quit hit, false if not.
	 */
	public boolean initiatedDeath(Character source) {
		for (CombatListener listener : listeners) {
			boolean initiateDeath = listener.initiatedDeath(source, character);
			if (!initiateDeath) {
				return false;
			}
		}
		return true;
	}
}
