package org.apollo.game.sync.task;

import org.apollo.game.model.NPC;

/**
 * A {@link SynchronizationTask} which does pre-synchronization work for the specified {@link NPC}.
 * @author Steve
 */
public class PreNPCSynchronizationTask extends SynchronizationTask {

	/**
	 * The npc.
	 */
	private final NPC npc;

	/**
	 * Creates the {@link PreNPSynchronizationTask} for the specified player.
	 * @param npc The npc.
	 */
	public PreNPCSynchronizationTask(NPC npc) {
		this.npc = npc;
	}

	@Override
	public void run() {
		npc.getWalkingQueue().pulse();
	}
}
