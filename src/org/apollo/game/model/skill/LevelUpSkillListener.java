package org.apollo.game.model.skill;

import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.util.LanguageUtil;

/**
 * A {@link SkillListener} which notifies the player when they have levelled up a skill.
 * @author Graham
 */
public final class LevelUpSkillListener extends SkillAdapter {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Creates the level up listener for the specified player.
	 * @param player The player.
	 */
	public LevelUpSkillListener(Player player) {
		this.player = player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.game.model.skill.SkillAdapter#levelledUp(org.apollo.game.model .SkillSet, int,
	 * org.apollo.game.model.Skill)
	 */
	@Override
	public void levelledUp(SkillSet set, int id, Skill skill) {
		final String name = Skill.getName(id);
		final String article = LanguageUtil.getIndefiniteArticle(name);
		player.sendMessage("You've just advanced " + article + " " + name + " level! You have reached level "
				+ skill.getMaximumLevel() + ".");
	}
}
