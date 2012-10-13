package org.apollo.game.model;

import java.util.ArrayList;
import java.util.List;

import org.apollo.game.event.impl.ProjectileEvent;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * A class representing characters attacking eachother.
 * @author Steve
 */
public final class MeleeSet {

	/**
	 * The list of projectiles.
	 */
	private final List<ProjectileEvent> projectiles = new ArrayList<ProjectileEvent>();

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
	private boolean autoRetaliating;

	/**
	 * Gets the auto retaliating flag.
	 * @return True if retaliating, false if not.
	 */
	public boolean isAutoRetaliating() {
		return autoRetaliating;
	}

	/**
	 * Sets the auto retaliating flag.
	 * @param autoRetaliating The auto retaliating flag.
	 */
	public void setAutoRetaliating(boolean autoRetaliating) {
		this.autoRetaliating = autoRetaliating;
	}

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
	private boolean underAttack;

	/**
	 * The character.
	 */
	@SuppressWarnings("unused")
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
	 * The task.
	 */
	private ScheduledTask task;

	/**
	 * Start a new melee class for the specified character.
	 * @param character The character.
	 */
	public MeleeSet(Character character) {
		this.character = character;
	}

	/**
	 * Sets the task.
	 * @param task The task.
	 */
	public void setTask(ScheduledTask task) {
		this.task = task;
	}

	/**
	 * Gets the interacting character.
	 * @return The interacting character.
	 */
	public Character getInteractingCharacter() {
		return interacter;
	}

	/**
	 * Sets the special.
	 * @param special The special.
	 */
	public void setSpecial(int special) {
		this.special = special;
	}

	/**
	 * Sets the interacting character.
	 * @param interacter The interacting character.
	 */
	public void setInteractingCharacter(Character interacter) {
		this.interacter = interacter;
	}

	/**
	 * Gets the using special flag.
	 * @return True if using special, false if not.
	 */
	public boolean isUsingSpecial() {
		return usingSpecial;
	}

	/**
	 * Sets the using special flag.
	 * @param usingSpecial the using special flag
	 */
	public void setUsingSpecial(boolean usingSpecial) {
		this.usingSpecial = usingSpecial;
	}

	/**
	 * Gets the magic flag.
	 * @return True if using magic, false if not.
	 */
	public boolean isUsingMagic() {
		return usingMagic;
	}

	/**
	 * Sets the using magic flag.
	 * @param usingMagic the using magic flag.
	 */
	public void setUsingMagic(boolean usingMagic) {
		this.usingMagic = usingMagic;
	}

	/**
	 * Gets the magic spell id.
	 * @return The magic spell id.
	 */
	public int getMagicSpellId() {
		return magicSpellId;
	}

	/**
	 * Gets the list of projectiles.
	 * @return The list of projectiles.
	 */
	public List<ProjectileEvent> getProjectiles() {
		return projectiles;
	}

	/**
	 * Sets the magic spell id.
	 * @param magicSpellId The magic spell id.
	 */
	public void setMagicSpellId(int magicSpellId) {
		this.magicSpellId = magicSpellId;
	}

	/**
	 * Gets the last attack.
	 * @return The last attack.
	 */
	public long getLastAttack() {
		return lastAttack;
	}

	/**
	 * Sets the last attack.
	 * @param lastAttack The last attack.
	 */
	public void setLastAttack(long lastAttack) {
		this.lastAttack = lastAttack;
	}

	/**
	 * Gets the attack timer.
	 * @return The attack timer.
	 */
	public int getAttackTimer() {
		return attackTimer;
	}

	/**
	 * Sets the attack timer.
	 * @param attackTimer The attack timer.
	 */
	public void setAttackTimer(int attackTimer) {
		this.attackTimer = attackTimer;
	}

	/**
	 * Gets the attacking flag.
	 * @return True if attacking, false if not.
	 */
	public boolean isAttacking() {
		return attacking;
	}

	/**
	 * Sets the attacking flag.
	 * @param attacking The attacking flag.
	 */
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	/**
	 * Gets the under attack flag.
	 * @return True if under attack, false if not.
	 */
	public boolean isUnderAttack() {
		return underAttack;
	}

	/**
	 * Sets the under attack flag.
	 * @param underAttack The under attack flag.
	 */
	public void setUnderAttack(boolean underAttack) {
		this.underAttack = underAttack;
	}

	/**
	 * Gets the dying flag.
	 * @return True if dying, false if not.
	 */
	public boolean isDying() {
		return dying;
	}

	/**
	 * Sets the dying flag.
	 * @param dying The dying flag.
	 */
	public void setDying(boolean dying) {
		this.dying = dying;
	}

	/**
	 * Gets the special.
	 * @return The special.
	 */
	public int getSpecial() {
		return special;
	}

	/**
	 * Gets the task before appending the hit.
	 * @return The task before appending the hit.
	 */
	public ScheduledTask getTask() {
		return task;
	}
}
