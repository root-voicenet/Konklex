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
	 * The character.
	 */
	private final Character character;

	/**
	 * Creates the level up listener for the specified character.
	 * @param character The character.
	 */
	public HitpointSkillListener(Character character) {
		this.character = character;
	}

	@Override
	public void levelledUp(SkillSet set, int id, Skill skill) {
		if (id == Skill.HITPOINTS)
			if (character.isControlling()) {
				((Player) character).send(new SetInterfaceTextEvent(4017, Integer.toString(skill.getMaximumLevel())));
			}
	}

	@Override
	public void skillUpdated(SkillSet set, int id, Skill skill) {
		if (id == Skill.HITPOINTS) {
			if (character.isControlling()) {
				((Player) character).send(new SetInterfaceTextEvent(4016, Integer.toString(skill.getCurrentLevel())));
			}
			if (skill.getCurrentLevel() <= 0 && !character.getMeleeSet().isDying()) {
				Character victim = character.getMeleeSet().getInteractingCharacter();
				if (victim != null) {
					if (victim.getHealth() <= 0 && !victim.getMeleeSet().isDying()) {
						victim.resetMeleeSet();
						victim.getBlockSet().add(SynchronizationBlock.createTurnToEntityBlock(-1));
						character.resetMeleeSet();
						character.getBlockSet().add(SynchronizationBlock.createTurnToEntityBlock(-1));
					}
				} Combat.appendDeath(victim, character); // actually, we are the victim. the victim is the source who killed us.
			}
		}
	}
}
