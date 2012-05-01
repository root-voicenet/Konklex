package org.apollo.game.model;

import org.apollo.game.model.def.NpcDefinition;

/**
 * NPC.java
 * @author The Wanderer
 */
public class NPC extends Character {

	/**
	 * The definition.
	 */
	private final NPCDefinition definition;

	/**
	 * Creates the NPC with the specified definition.
	 * @param definition The definition.
	 * @param pos The position
	 */
	public NPC(NPCDefinition definition, Position pos) {
		super(pos);
		this.definition = definition;
		init(0);
	}

	/**
	 * Creates the NPC with the specified definition.
	 * @param definition The definition.
	 * @param pos The position.
	 * @param health The health.
	 */
	public NPC(NPCDefinition definition, Position pos, int health) {
		super(pos);
		this.definition = definition;
		init(health);
	}

	/**
	 * Gets the NPC definition.
	 * @return The NPC definition.
	 */
	public NpcDefinition getDefinition() {
		return NpcDefinition.forId(definition.getId());
	}

	/**
	 * Initialize the npc.
	 * @param health the health
	 */
	private void init(int health) {
		Skill skill = new Skill(this.getSkillSet().getSkill(Skill.HITPOINTS).getExperience(), health > 0 ? health : 10, health > 0 ? health : 10);
		this.getSkillSet().setSkill(Skill.HITPOINTS, skill);
		this.setHealth(health > 0 ? health : 10);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.model.Character#send(org.apollo.game.event.Event)
	 */
	@Override
	public void send(org.apollo.game.event.Event event) {
	}
}