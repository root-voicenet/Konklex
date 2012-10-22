package org.apollo.game.sync.task;

import java.util.Collection;
import java.util.List;

import org.apollo.game.event.Event;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;

/**
 * An {@link SynchronizationTask} that ensures only the required locals are in the player's lists.
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
		final List<Event> localEvents = player.getLocalEventList();
		final Collection<Event> events = World.getWorld().getRegionManager().getLocalEvents(player);
		for (final Event event : localEvents)
			if (!events.contains(event))
				localEvents.remove(event);
	}

}
