package org.apollo.game.sync.task;

import java.util.Collection;
import java.util.List;

import org.apollo.game.event.Event;
import org.apollo.game.event.impl.CreateGroundEvent;
import org.apollo.game.event.impl.MapEvent;
import org.apollo.game.event.impl.PositionEvent;
import org.apollo.game.model.GroundItem;
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

    @Override
    public void run() {
	int added = 0;
	final List<Event> localEvents = player.getLocalEventList();
	final Collection<Event> events = World.getWorld().getRegionManager().getLocalEvents(player);
	for (final Event event : events) {
	    if (added >= EVENTS_PER_CYCLE)
		break;
	    if (!localEvents.contains(event))
		if (event instanceof CreateGroundEvent) {
		    final CreateGroundEvent ground = (CreateGroundEvent) event;
		    final GroundItem item = ground.getGroundItem();
		    if (item.getControllerName().equals(player.getName()) || item.getPulses() == 0) {
			localEvents.add(event);
			player.send(new PositionEvent(player.getLastKnownRegion(), ground.getPosition()));
			player.send(event);
			added++;
		    }
		} else if (localEvents.add(event)) {
		    if (event instanceof MapEvent) {
			final MapEvent map = (MapEvent) event;
			player.send(new PositionEvent(player.getLastKnownRegion(), map.getPosition()));
		    }
		    player.send(event);
		    added++;
		}
	}
    }

}