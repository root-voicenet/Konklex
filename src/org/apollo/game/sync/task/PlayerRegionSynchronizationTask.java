package org.apollo.game.sync.task;

import java.util.Collection;
import java.util.List;

import org.apollo.game.event.impl.GroundItemEvent;
import org.apollo.game.event.impl.ObjectEvent;
import org.apollo.game.event.impl.PositionEvent;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;

/**
 * An {@link SynchronizationTask} that sends the player region events.
 * @author Steve
 */
public final class PlayerRegionSynchronizationTask extends SynchronizationTask {

    /**
     * The maximum number of players to load per cycle. This prevents the update
     * packet from becoming too large (the client uses a 5000 byte buffer) and
     * also stops old spec PCs from crashing when they login or teleport.
     */
    private static final int EVENTS_PER_CYCLE = 20;

    /**
     * The player.
     */
    private final Player player;

    /**
     * Creates a new player region synchronization task.
     * @param player The player.
     */
    public PlayerRegionSynchronizationTask(Player player) {
	this.player = player;
    }

    @SuppressWarnings("unused")
    @Override
    public void run() {
	final List<GameObject> localObjects = player.getLocalGameObjectList();
	final int oldLocalObjects = localObjects.size();
	final List<GroundItem> localGroundItems = player.getLocalGroundItemList();
	final int oldLocalGroundItems = localGroundItems.size();
	int added = 0;
	final Collection<GameObject> objects = World.getWorld().getRegionManager().getLocalObjects(player);
	for (final GameObject object : objects) {
	    if (added >= EVENTS_PER_CYCLE)
		break;
	    if (!localObjects.contains(object)) {
		player.send(new PositionEvent(player.getLastKnownRegion(), object.getLocation()));
		player.send(new ObjectEvent(object));
		if (!object.isRemoving())
		    localObjects.add(object);
		added++;
	    }
	}
	added = 0;
	final Collection<GroundItem> groundItems = World.getWorld().getRegionManager().getLocalGroundItems(player);
	for (final GroundItem groundItem : groundItems) {
	    if (added >= EVENTS_PER_CYCLE)
		break;
	    if (!localGroundItems.contains(groundItem))
		if (groundItem.getControllerName().equalsIgnoreCase(player.getName()) || groundItem.getPulses() == 0) {
		    final Item item = groundItem.getItem();
		    player.send(new PositionEvent(player.getLastKnownRegion(), groundItem.getPosition()));
		    player.send(new GroundItemEvent(groundItem));
		    if (!groundItem.isRemoving())
			localGroundItems.add(groundItem);
		    added++;
		}
	}
    }

}