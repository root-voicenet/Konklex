package org.apollo.game.minigame;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apollo.Service;
import org.apollo.game.GameConstants;
import org.apollo.game.model.Player;
import org.apollo.util.NamedThreadFactory;

/**
 * The {@link MinigameService} class schedules and manages the execution of the
 * {@link MinigamePulseHandler} class.
 * 
 * @author Steve
 */
public final class MinigameService extends Service {

	/**
	 * The scheduled executor service.
	 */
	private final ScheduledExecutorService scheduledExecutor = Executors
			.newSingleThreadScheduledExecutor(new NamedThreadFactory(
					"MinigameService"));

	/**
	 * A queue of the current minigames.
	 */
	private final Queue<Minigame> minigames = new ConcurrentLinkedQueue<Minigame>();

	/**
	 * Adds a minigame.
	 * 
	 * @param minigame
	 *            The minigame.
	 */
	public void addMinigame(Minigame minigame) {
		minigames.add(minigame);
	}

	/**
	 * Called when a player disconnects.
	 * 
	 * @param player
	 *            The player that is disconnecting.
	 * @deprecated Only allow exitInitialEvents() to access this.
	 */
	@Deprecated
	public void playerDisconnected(Player player) {
		synchronized (this) {
			for (final Minigame minigame : minigames)
				if (minigame != null) {
					if (minigame.getTeam(player) != -1)
						minigame.playerDisconnected(player);
				} else
					removeMinigame(minigame);
		}
	}

	/**
	 * Called every pulse.
	 */
	public void pulse() {
		synchronized (this) {
			for (final Minigame minigame : minigames)
				if (minigame != null)
					minigame.pulse();
				else
					removeMinigame(minigame);
		}
	}

	/**
	 * Removes a minigame.
	 * 
	 * @param minigame
	 *            The minigame.
	 */
	public void removeMinigame(Minigame minigame) {
		minigames.remove(minigame);
	}

	/**
	 * Starts the minigame service.
	 */
	@Override
	public void start() {
		scheduledExecutor.scheduleAtFixedRate(new MinigamePulseHandler(this),
				GameConstants.MINIGAME_DELAY, GameConstants.MINIGAME_DELAY,
				TimeUnit.MILLISECONDS);
	}
}
