package org.apollo.game.sync.task;

import org.apollo.game.model.Npc;

/**
 * A {@link SynchronizationTask} which does synchronization work for the specified {@link Npc}.
 * @author Steve
 */
public final class NpcSynchronizationTask extends SynchronizationTask {

	/**
	 * The npc.
	 */
	private final Npc npc;

	/**
	 * Creates the {@link NpcSynchronizationTask} for the specified npc.
	 * @param npc The npc.
	 */
	public NpcSynchronizationTask(Npc npc) {
		this.npc = npc;
	}

	@Override
	public void run() {
		npc.getWalkingQueue().pulse();
		npc.setTeleporting(false);
		npc.resetBlockSet();
	}
}
