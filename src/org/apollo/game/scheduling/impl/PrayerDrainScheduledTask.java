package org.apollo.game.scheduling.impl;

import org.apollo.game.model.Character;
import org.apollo.game.model.Skill;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} which drains the players prayer.
 * @author Steve
 */
public final class PrayerDrainScheduledTask extends ScheduledTask {

	/**
	 * The character.
	 */
	private final Character character;

	/**
	 * The prayers that are active.
	 */
	private final int prayers;

	/**
	 * The prayer drain scheduled task.
	 * @param character The character.
	 * @param ticks The ticks.
	 * @param prayers The prayers.
	 */
	public PrayerDrainScheduledTask(Character character, int ticks, int prayers) {
		super(ticks, false);
		this.prayers = prayers;
		this.character = character;
	}

	@Override
	public void execute() {
		Skill set = character.getSkillSet().getSkill(Skill.PRAYER);
		final int LEVEL = set.getCurrentLevel();
		Skill newSet = new Skill(set.getExperience(), LEVEL - prayers, set.getMaximumLevel());
		character.getSkillSet().setSkill(Skill.PRAYER, newSet);
		stop();
	}

}
