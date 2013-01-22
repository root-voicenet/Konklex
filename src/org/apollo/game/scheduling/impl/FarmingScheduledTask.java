package org.apollo.game.scheduling.impl;

import org.apollo.game.model.Player;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} that updates farming configurations.
 * @author Steve
 */
public final class FarmingScheduledTask extends ScheduledTask {

	/**
	 * The player this task is scheduled too.
	 */
	private final Player player;

	/**
	 * Schedules a new task for the specified player.
	 * @param player The player to register the schedule too.
	 */
	public FarmingScheduledTask(Player player) {
		super(25, true);
		this.player = player;
	}

	@Override
	public void execute() {
		if (!player.isActive()) {
			stop();
		}
		else {
			player.getAllotment().doCalculations();
			player.getBushes().doCalculations();
			player.getFlowers().doCalculations();
			player.getHerbs().doCalculations();
			player.getHops().doCalculations();
			player.getSpecialPlantOne().doCalculations();
			player.getSpecialPlantTwo().doCalculations();
			player.getFruitTrees().doCalculations();
		}
	}
}