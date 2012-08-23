package org.apollo.game.model.skill;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;

/**
 * A {@link SkillListener} which sends config values when a player levels up
 * prayer.
 * @author Steve
 */
public final class PrayerSkillListener extends SkillAdapter {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Creates the level up listener for the specified player.
	 * @param player The player.
	 */
	public PrayerSkillListener(Player player) {
		this.player = player;
	}

	@Override
	public void levelledUp(SkillSet set, int id, Skill skill) {
		if (id == Skill.PRAYER)
			player.send(new SetInterfaceTextEvent(4013, Integer.toString(skill.getMaximumLevel())));
	}

	@Override
	public void skillUpdated(SkillSet set, int id, Skill skill) {
		if (id == Skill.PRAYER)
			player.send(new SetInterfaceTextEvent(4012, Integer.toString(skill.getCurrentLevel())));
	}
}
