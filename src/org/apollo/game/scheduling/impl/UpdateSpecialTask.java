package org.apollo.game.scheduling.impl;

import org.apollo.game.model.Player;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledEvent} for updating the special.
 * @author Steve
 */
public final class UpdateSpecialTask extends ScheduledTask {

    /**
     * The player this task is scheduled to.
     */
    private final Player player;

    /**
     * Create a new update special task.
     * @param player The player who this task is scheduled to.
     */
    public UpdateSpecialTask(Player player) {
	super(100, true);
	this.player = player;
    }

    @Override
    public void execute() {
	if (!player.isActive())
	    stop();
	else {
	    int special = player.getMeleeSet().getSpecial();
	    special += 10;
	    if (special > 100)
		special = 100;
	    player.getMeleeSet().setSpecial(special);
	    // now sent it to the client
	}
    }
}
