package org.apollo.game.sync.task;

import org.apollo.game.model.Npc;

/**
 * A {@link SynchronizationTask} which does pre-synchronization work for the
 * specified {@link Npc}.
 * @author Steve
 */
public class PreNpcSynchronizationTask extends SynchronizationTask {

    /**
     * The npc.
     */
    private final Npc npc;

    /**
     * Creates the {@link PreNPSynchronizationTask} for the specified player.
     * @param npc The npc.
     */
    public PreNpcSynchronizationTask(Npc npc) {
	this.npc = npc;
    }

    @Override
    public void run() {
	npc.getWalkingQueue().pulse();
    }
}
