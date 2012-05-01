package org.apollo.game.model.skill;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.util.MysqlUtil;

/**
 * Saves the player's skillset to the highscores.
 * @author Steve
 */
public final class HiscoreSaver {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(HiscoreSaver.class.getName());

	/**
	 * Create the record; only if not created before.
	 * @param player The player to create the record for.
	 */
	public static void create(Player player) {
		try {
			MysqlUtil.query("INSERT IGNORE INTO crsps_konklex.hiscores (`user`) VALUES ('" + player.getName() + "')");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Could not save " + player.getName() + "'s hiscores.", e);
		}
	}

	/**
	 * Save the player's hiscores.
	 * @param player The player to save the hiscores.
	 */
	public static void save(Player player) {
		try {
			SkillSet skills = player.getSkillSet();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < skills.size(); i++) {
				Skill skill = skills.getSkill(i);
				builder.append((i == 0 ? "SET " : "") + "`" + i + "` = '" + skill.getExperience() + "'" + (i == skills.size() - 1 ? "" : ", "));
			}
			MysqlUtil.query("UPDATE crsps_konklex.hiscores " + builder.toString() + " WHERE `user` = '" + player.getName() + "'");
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Could not save " + player.getName() + "'s hiscores.", e);
		}
	}
}
