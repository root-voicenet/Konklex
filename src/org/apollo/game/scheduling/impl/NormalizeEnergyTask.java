package org.apollo.game.scheduling.impl;

import org.apollo.game.event.impl.UpdateRunEnergyEvent;
import org.apollo.game.model.Player;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link Tickable} task that increases the run energy when the player is idle.
 * @author Steve
 */
public class NormalizeEnergyTask extends ScheduledTask {

	/*
	 * TODO Tasks Implement this with the agility level.
	 */
	/**
	 * The player this task is scheduled too.
	 */
	private final Player player;

	/**
	 * Schedules a new task for the specified player.
	 * @param player The player to register the schedule too.
	 */
	public NormalizeEnergyTask(Player player) {
		super(10, true);
		this.player = player;
	}

	@Override
	public void execute() {
		if (!player.isActive()) {
			stop();
		} else {
			if (!player.getWalkingQueue().getRunningQueue()) {
				if (player.getRunEnergy() < 100) {
					player.setRunEnergy(player.getRunEnergy() + 1);
					player.send(new UpdateRunEnergyEvent(player.getRunEnergy()));
				}
			}
		}
	}
}