package org.apollo.game.scheduling.impl;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledEvent} for updating the special.
 * @author Steve
 */
public final class UptimeTask extends ScheduledTask {
	
	/**
	 * The uptime.
	 */
	private int i = 0;

	/**
	 * Create a new uptime task.
	 */
	public UptimeTask() {
		super(25, true);
	}

	@Override
	public void execute() {
		World.getWorld().setUptime(i++);
		for (Player player : World.getWorld().getPlayerRepository()) {
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
