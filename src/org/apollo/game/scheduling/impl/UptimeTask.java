package org.apollo.game.scheduling.impl;

import org.apollo.api.FrontendService;
import org.apollo.api.method.impl.TimeMethod;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} for updating the server time.
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
		if (World.getWorld().getContext() != null) {
			FrontendService service = World.getWorld().getContext().getService(FrontendService.class);
			if (service != null) {
				service.sendAll(new TimeMethod(World.getWorld().getUptime()));
			}
		}
	}
}
