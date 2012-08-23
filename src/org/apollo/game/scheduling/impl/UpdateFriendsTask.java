package org.apollo.game.scheduling.impl;

import org.apollo.game.event.impl.FriendsListEvent;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} for sending the {@link FriendsListEvent} to the
 * player.
 * @author Steve
 */
public class UpdateFriendsTask extends ScheduledTask {

	/**
	 * Create a new build friends task.
	 */
	public UpdateFriendsTask() {
		super(10, true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.scheduling.ScheduledTask#execute()
	 */
	@Override
	public void execute() {
		World.getWorld().getMessaging().dispatch();
	}
}
