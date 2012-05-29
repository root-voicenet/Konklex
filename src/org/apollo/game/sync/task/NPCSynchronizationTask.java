package org.apollo.game.sync.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apollo.game.event.impl.NpcSynchronizationEvent;
import org.apollo.game.model.Config;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.sync.block.SynchronizationBlockSet;
import org.apollo.game.sync.seg.AddNpcSegment;
import org.apollo.game.sync.seg.MovementSegment;
import org.apollo.game.sync.seg.RemoveCharacterSegment;
import org.apollo.game.sync.seg.SynchronizationSegment;

/**
 * NPCSynchronizzationTask.java
 * @author The Wanderer & Zuppers
 */
public class NpcSynchronizationTask extends SynchronizationTask {

    /** The Constant NEW_NPCS_PER_CYCLE. */
    private static final int NEW_NPCS_PER_CYCLE = 20;

    /**
     * The player.
     */
    private final Player player;

    /**
     * Creates the {@link NpcSynchronizationTask} for the specified player.
     * @param player The player.
     */
    public NpcSynchronizationTask(Player player) {
	this.player = player;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
	SynchronizationBlockSet blockSet = player.getBlockSet();
	final List<Npc> localNPCs = player.getLocalNpcList();
	final int oldLocalPlayers = localNPCs.size();
	final List<SynchronizationSegment> segments = new ArrayList<SynchronizationSegment>();
	for (final Iterator<Npc> it = localNPCs.iterator(); it.hasNext();) {
	    final Npc n = it.next();
	    if (!n.isActive() || !Config.SERVER_NPCS || n.isTeleporting()
		    || n.getPosition().getLongestDelta(player.getPosition()) > player.getViewingDistance()) {
		it.remove();
		segments.add(new RemoveCharacterSegment());
	    } else {
		segments.add(new MovementSegment(n.getBlockSet(), n.getDirections()));
	    }
	}
	int added = 0;
	final Collection<Npc> repository = World.getWorld().getRegionManager().getLocalNpcs(player);
	for (final Npc n : repository) {
	    if (localNPCs.size() >= 255 || !Config.SERVER_NPCS) {
		break;
	    } else {
		if (added >= NEW_NPCS_PER_CYCLE) {
		    break;
		}
		if (!localNPCs.contains(n)) {
		    localNPCs.add(n);
		    added++;
		    blockSet = n.getBlockSet();
		    segments.add(new AddNpcSegment(blockSet, n.getIndex(), n.getPosition(), n.getDefinition().getId()));
		}
	    }
	}
	final NpcSynchronizationEvent event = new NpcSynchronizationEvent(player.getPosition(), oldLocalPlayers,
		segments);
	player.send(event);
    }
}