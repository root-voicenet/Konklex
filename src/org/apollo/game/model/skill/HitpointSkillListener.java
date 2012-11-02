package org.apollo.game.model.skill;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.game.minigame.MinigameService;
import org.apollo.game.model.Character;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.model.World;
import org.apollo.game.model.inter.melee.Combat;
import org.apollo.game.model.inter.melee.Prayer.Prayers;

/**
 * A {@link SkillListener} which sends config values when a player levels up their health.
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

	@SuppressWarnings("deprecation")
	@Override
	public void skillUpdated(SkillSet set, int id, Skill skill) {
		if (id == Skill.HITPOINTS) {
			if (character.isControlling()) {
				((Player) character).send(new SetInterfaceTextEvent(4016, Integer.toString(skill.getCurrentLevel())));
			}
			if (skill.getCurrentLevel() <= 0 && !character.getMeleeSet().isDying()) {
				Character victim = character.getMeleeSet().getInteractingCharacter(); // we killed character
				MinigameService service = World.getWorld().getContext().getService(MinigameService.class);
				if (service.isPlayerOnline(character)) {
					service.playerDied(character, victim);
					return;
				}
				Combat.appendDeath(victim, character);
			}
			else if (skill.getCurrentLevel() <= 10) {
				if (character.getPrayers().contains(Prayers.REDEMPTION)) {
					Skill prayer = new Skill(skill.getExperience(), 0, skill.getMaximumLevel());
					int healthToAdd = (int) (character.getSkillSet().getSkill(Skill.PRAYER).getMaximumLevel() * 2.50);
					character.addHealth(healthToAdd);
					set.setSkill(id, prayer);
				}
			}
		}
	}
}
