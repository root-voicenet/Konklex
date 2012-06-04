package org.apollo.game.sync.task;

import java.util.Collection;
import java.util.List;

import org.apollo.game.model.GameObject;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;

/**
 * An {@link SynchronizationTask} that ensures only the required locals are in
 * the player's lists.
 * @author Steve
 */
public final class PrePlayerRegionSynchronizationTask extends SynchronizationTask {

    /**
     * The player.
     */
    private final Player player;

    /**
     * Creates the new region sync task.
     * @param player The player.
     */
    public PrePlayerRegionSynchronizationTask(Player player) {
	this.player = player;
    }

    @Override
    public void run() {
	final List<GameObject> localObjects = player.getLocalGameObjectList();
	final List<GroundItem> localGroundItems = player.getLocalGroundItemList();
	final Collection<GameObject> objects = World.getWorld().getRegionManager().getLocalObjects(player);
	final Collection<GroundItem> groundItems = World.getWorld().getRegionManager().getLocalGroundItems(player);
	for (final GameObject object : localObjects)
	    if (!objects.contains(object))
		localObjects.remove(object);
	for (final GroundItem groundItem : localGroundItems)
	    if (!groundItems.contains(groundItem))
		localGroundItems.remove(groundItem);
    }

}
