package org.apollo.game.scheduling.impl;

import org.apollo.game.model.GameObject;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * A {@link ScheduledTask} which processes all region local's currently in game.
 * @author Steve
 */
public final class ProcessRegionTask extends ScheduledTask {

    /**
     * Creates the ground item process task.
     */
    public ProcessRegionTask() {
	super(0, true);
    }

    /*
     * (non-Javadoc)
     * @see org.apollo.game.scheduling.ScheduledTask#execute()
     */
    @Override
    public void execute() {
	for (GroundItem item : World.getWorld().getItems()) {
	    if (item.getPulses() != 0) {
		item.decreasePulses();
	    }
	    if (item.isRemoving()) {
		World.getWorld().unregister(item);
	    }
	}
	for (GameObject object : World.getWorld().getObjects()) {
	    if (object.isRemoving()) {
		World.getWorld().unregister(object);
	    }
	}
	stop();
    }
}