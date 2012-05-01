package org.apollo.game.sync.task;

import org.apollo.game.model.NPC;

/**
 * A {@link SynchronizationTask} which does post-synchronization work for the specified {@link NPC}.
 * @author Steve
 */
public class PostNPCSynchronizationTask extends SynchronizationTask {

	/**
	 * The npc.
	 */
	private final NPC npc;

	/**
	 * Creates the {@link PostNPSynchronizationTask} for the specified player.
	 * @param npc The npc.
	 */
	public PostNPCSynchronizationTask(NPC npc) {
		this.npc = npc;
	}

	@Override
	public void run() {
		npc.setTeleporting(false);
		npc.resetBlockSet();
	}
}
