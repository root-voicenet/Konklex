package org.apollo.game.sync;

import org.apollo.game.GameService;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.inter.melee.Combat;
import org.apollo.game.sync.task.PostNpcSynchronizationTask;
import org.apollo.game.sync.task.SynchronizationTask;
import org.apollo.util.CharacterRepository;

/**
 * An implementation of {@link ClientSynchronizer} which runs in a single thread (the {@link GameService} thread from
 * which this is called). Each client is processed sequentially. Therefore this class will work well on machines with a
 * single core/processor. The {@link ParallelClientSynchronizer} will work better on machines with multiple
 * cores/processors, however, both classes will work.
 * @author Steve
 */
public final class SequentialNpcSynchronizer extends ClientSynchronizer {

	@Override
	public void synchronize() {
		final CharacterRepository<Npc> npcs = World.getWorld().getNpcRepository();
		final CharacterRepository<Player> players = World.getWorld().getPlayerRepository();

		for (final Npc npc : npcs) {
			final SynchronizationTask task = new PostNpcSynchronizationTask(npc);
			task.run();
		}

		for (final Npc npc : npcs) {
			final SynchronizationTask task = new SynchronizationTask() {

				@Override
				public void run() {
					Combat.process(npc);
				}

			};
			task.run();
		}
		for (final Player player : players) {
			final SynchronizationTask task = new SynchronizationTask() {

				@Override
				public void run() {
					Combat.process(player);
				}

			};
			task.run();
		}
	}
}
