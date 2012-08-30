package org.apollo.game.sync.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apollo.game.event.impl.NpcSynchronizationEvent;
import org.apollo.game.model.Config;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.sync.block.SynchronizationBlock;
import org.apollo.game.sync.block.SynchronizationBlockSet;
import org.apollo.game.sync.seg.AddNpcSegment;
import org.apollo.game.sync.seg.MovementSegment;
import org.apollo.game.sync.seg.RemoveCharacterSegment;
import org.apollo.game.sync.seg.SynchronizationSegment;

/**
 * An {@link SynchronizationTask} that sends the npcs.
 * @author Steve
 */
public final class NpcSynchronizationTask extends SynchronizationTask {

	/** The maximum number of npcs to load per cycle. This prevents the update
	 * packet from becoming too large (the client uses a 5000 byte buffer) and
	 * also stops old spec PCs from crashing when they login or teleport.
	 */
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
		
		int added = 0;
		final Collection<Npc> repository = World.getWorld().getRegionManager().getLocalNpcs(player);
		
		for (final Iterator<Npc> it = localNPCs.iterator(); it.hasNext();) {
		    final Npc n = it.next();
		    final boolean check = n.getPosition().getHeight() == player.getPosition().getHeight();
		    if (!n.isActive() || n.isTeleporting() || !check || n.getPosition().getLongestDelta(player.getPosition()) > player.getViewingDistance()) {
				it.remove();
				segments.add(new RemoveCharacterSegment());
		    } else
		    	segments.add(new MovementSegment(n.getBlockSet(), n.getDirections()));
		}
		
		for (final Npc n : repository)
			if (localNPCs.size() >= 255 || !Config.SERVER_NPCS)
				break;
			else {
				if (added >= NEW_NPCS_PER_CYCLE)
					break;
				if (!localNPCs.contains(n)) {
					localNPCs.add(n);
					added++;
					blockSet = n.getBlockSet();
					if (n.getFace() > 1) {
						blockSet = blockSet.clone();
						blockSet.add(SynchronizationBlock.createTurnToPositionBlock(getPositon(n)));
					}
					segments.add(new AddNpcSegment(blockSet, n.getIndex(), n.getPosition(), n.getDefinition().getId()));
				}
			}
		final NpcSynchronizationEvent event = new NpcSynchronizationEvent(player.getPosition(), oldLocalPlayers, segments);
		player.send(event);
	}

	/**
	 * Gets the facing position.
	 * @param n The npc.
	 * @return The facing position.
	 */
	private Position getPositon(Npc n) {
		switch (n.getFace()) {
			case 5:
				return n.getPosition().transform(-1, 0, 0);
			case 4:
				return n.getPosition().transform(1, 0, 0);
			case 3:
				return n.getPosition().transform(0, -1, 0);
			case 2:
				return n.getPosition().transform(0, 1, 0);
			default:
				return n.getPosition();
		}
	}
}