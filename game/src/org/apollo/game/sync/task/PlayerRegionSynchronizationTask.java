package org.apollo.game.sync.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apollo.game.event.Event;
import org.apollo.game.event.impl.CreateGroundEvent;
import org.apollo.game.event.impl.RegionUpdateEvent;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.region.RegionCoordinates;
import org.apollo.game.model.region.RegionManager;

/**
 * An {@link SynchronizationTask} that sends the player region events.
 * @author Steve
 */
public final class PlayerRegionSynchronizationTask extends SynchronizationTask {

	/**
	 * The maximum number of players to load per cycle. This prevents the update packet from becoming too large (the
	 * client uses a 5000 byte buffer) and also stops old spec PCs from crashing when they login or teleport.
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
		final List<Event> regionEvents = new ArrayList<Event>();

		for (final Event event : localEvents)
			if (!events.contains(event)) {
				localEvents.remove(event);
			}

		for (final Event event : events) {
			if (added >= EVENTS_PER_CYCLE) {
				break;
			}
			if (!localEvents.contains(event))
				if (event instanceof CreateGroundEvent) {
					final CreateGroundEvent ground = (CreateGroundEvent) event;
					final GroundItem item = ground.getGroundItem();
					if (item.getControllerName().equals("null") || item.getControllerName().equals(player.getName())
							|| item.getPulses() == 0) {
						localEvents.add(event);
						regionEvents.add(event);
						added++;
					}
				}
				else if (localEvents.add(event)) {
					regionEvents.add(event);
					added++;
				}
		}

		if (added > 0) {
			final int REGION_SIZE = RegionManager.REGION_SIZE;
			final RegionCoordinates coordinates = player.getRegion().getCoordinates();
			final Position position = new Position(coordinates.getX() * REGION_SIZE, coordinates.getY() * REGION_SIZE);
			player.send(new RegionUpdateEvent(player.getLastKnownRegion(), position, regionEvents));
		}
	}

}