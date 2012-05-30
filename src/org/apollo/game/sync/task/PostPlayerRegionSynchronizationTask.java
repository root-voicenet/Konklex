package org.apollo.game.sync.task;

import org.apollo.game.model.GameObject;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.World;

/**
 * An {@link SynchronizationTask} that clears the worlds removable objects.
 * @author Steve
 */
public final class PostPlayerRegionSynchronizationTask extends SynchronizationTask {

    @Override
    public void run() {
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
    }

}
