package org.apollo.game.model.skill;

import org.apollo.game.event.impl.UpdateSkillEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Skill;
import org.apollo.game.model.SkillSet;
import org.apollo.game.sync.block.SynchronizationBlock;

/**
 * A {@link SkillListener} which synchronizes the state of a {@link SkillSet}
 * with a client.
 * @author Graham
 */
public final class SynchronizationSkillListener extends SkillAdapter {

    /**
     * The player.
     */
    private final Player player;

    /**
     * Creates the skill synchronization listener.
     * @param player The player.
     */
    public SynchronizationSkillListener(Player player) {
	this.player = player;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.game.model.skill.SkillAdapter#levelledUp(org.apollo.game.model
     * .SkillSet, int, org.apollo.game.model.Skill)
     */
    @Override
    public void levelledUp(SkillSet set, int id, Skill skill) {
	player.getBlockSet().add(SynchronizationBlock.createAppearanceBlock(player));
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.game.model.skill.SkillAdapter#skillsUpdated(org.apollo.game
     * .model.SkillSet)
     */
    @Override
    public void skillsUpdated(SkillSet set) {
	for (int id = 0; id < set.size(); id++)
	    player.send(new UpdateSkillEvent(id, set.getSkill(id)));
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.game.model.skill.SkillAdapter#skillUpdated(org.apollo.game
     * .model.SkillSet, int, org.apollo.game.model.Skill)
     */
    @Override
    public void skillUpdated(SkillSet set, int id, Skill skill) {
	player.send(new UpdateSkillEvent(id, skill));
    }
}
