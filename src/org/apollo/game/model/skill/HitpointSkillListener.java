package org.apollo.game.model.skill;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.model.inter.melee.Combat;
import org.apollo.game.model.Character;
import org.apollo.game.sync.block.SynchronizationBlock;

/**
 * A {@link SkillListener} which sends config values when a player levels up
 * their health.
 * @author Steve
 */
public final class HitpointSkillListener extends SkillAdapter {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Creates the level up listener for the specified player.
	 * @param player The player.
	 */
	public HitpointSkillListener(Player player) {
		this.player = player;
	}

	@Override
	public void levelledUp(SkillSet set, int id, Skill skill) {
		if (id == Skill.HITPOINTS)
			player.send(new SetInterfaceTextEvent(4017, Integer.toString(skill.getMaximumLevel())));
	}

	@Override
	public void skillUpdated(SkillSet set, int id, Skill skill) {
		if (id == Skill.HITPOINTS) {
			player.send(new SetInterfaceTextEvent(4016, Integer.toString(skill.getCurrentLevel())));
			if (skill.getCurrentLevel() <= 0 && !player.getMeleeSet().isDying()) {
				if (player.getMeleeSet().getInteractingCharacter() != null) {
					Character victim = player.getMeleeSet().getInteractingCharacter();
					if (victim.getHealth() <= 0 && !victim.getMeleeSet().isDying()) {
						victim.getMeleeSet().setAttacking(false);
						victim.getMeleeSet().setUnderAttack(false);
						victim.getMeleeSet().setInteractingCharacter(null);
						victim.getBlockSet().add(SynchronizationBlock.createTurnToEntityBlock(-1));
						player.getMeleeSet().setAttacking(false);
						player.getMeleeSet().setUnderAttack(false);
						player.getMeleeSet().setInteractingCharacter(null);
						player.getBlockSet().add(SynchronizationBlock.createTurnToEntityBlock(-1));
						Combat.appendDeath(victim, player);
					}
				} else
					Combat.appendDeath(null, player);
			}
		}
	}
}
