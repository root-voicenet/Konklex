package org.apollo.game.scheduling.impl;

import org.apollo.game.event.impl.FriendEvent;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} for sending the {@link FriendEvent} to the player.
 * @author Steve
 */
public class UpdateFriendsTask extends ScheduledTask {

	/**
	 * The instance.
	 */
	private static UpdateFriendsTask instance;

	/**
	 * Start the task.
	 */
	public static void start() {
		if (instance == null) {
			instance = new UpdateFriendsTask();
		}
	}

	/**
	 * Create a new build friends task.
	 */
	public UpdateFriendsTask() {
		super(10, true);
		World.getWorld().schedule(this);
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
