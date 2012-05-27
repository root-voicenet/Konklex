package org.apollo.game.scheduling.impl;

import org.apollo.game.model.Player;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} for processing objects.
 * @author Steve
 */
public class UpdateObjectsTask extends ScheduledTask {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Start a new update object task.
	 * @param player The player this update is scheduled too.
	 */
	public UpdateObjectsTask(Player player) {
		super(10, true);
		this.player = player;
	}

	@Override
	public void execute() {
		if (!player.isActive()) {
			stop();
		} else {
			player.getObjectSet().process();
		}
	}
}
