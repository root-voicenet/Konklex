package org.apollo.game.scheduling.impl;

import org.apollo.game.event.impl.SystemUpdateEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} that processes a system update timer for the players
 * online.
 * @author Steve
 */
public class SystemUpdateTask extends ScheduledTask {

    /**
     * Flag for the server updating task.
     */
    private static boolean SERVER_UPDATING = false;

    /**
     * Gets the system update flag.
     * @return True if running, false if not.
     */
    public static boolean isUpdating() {
	return SERVER_UPDATING;
    }

    /**
     * Starts the system update task.
     * @return True if started, false if already started.
     */
    public static boolean start() {
	if (!SERVER_UPDATING) {
	    new SystemUpdateTask(6);
	    return true;
	} else
	    return false;
    }

    /**
     * Flag for started or not.
     */
    private boolean started;

    /**
     * The time.
     */
    private final int time;

    /**
     * Construct the system update task.
     * @param time The time (in minutes) to cycle threw the update event.
     */
    public SystemUpdateTask(int time) {
	super(time * 60, true);
	this.time = time;
	SERVER_UPDATING = true;
	World.getWorld().schedule(this);
    }

    @Override
    public void execute() {
	if (!started) {
	    for (final Player player : World.getWorld().getPlayerRepository())
		player.send(new SystemUpdateEvent(time * 100));
	    started = true;
	} else {
	    for (final Player player : World.getWorld().getPlayerRepository())
		player.logout();
	    stop();
	}
    }
}
