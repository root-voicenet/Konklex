package org.apollo.game.sync.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apollo.game.event.impl.NPCSynchronizationEvent;
import org.apollo.game.model.Config;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.sync.block.SynchronizationBlockSet;
import org.apollo.game.sync.seg.AddNpcSegment;
import org.apollo.game.sync.seg.MovementSegment;
import org.apollo.game.sync.seg.RemoveCharacterSegment;
import org.apollo.game.sync.seg.SynchronizationSegment;
import org.apollo.util.CharacterRepository;

/**
 * NPCSynchronizzationTask.java
 * @author The Wanderer & Zuppers
 */
public class NPCSynchronizationTask extends SynchronizationTask {

	/** The Constant NEW_NPCS_PER_CYCLE. */
	private static final int NEW_NPCS_PER_CYCLE = 20;

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * Creates the {@link NPCSynchronizationTask} for the specified player.
	 * @param player The player.
	 */
	public NPCSynchronizationTask(Player player) {
		this.player = player;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		SynchronizationBlockSet blockSet = player.getBlockSet();
		List<Npc> localNPCs = player.getLocalNpcList();
		int oldLocalPlayers = localNPCs.size();
		List<SynchronizationSegment> segments = new ArrayList<SynchronizationSegment>();
		for (Iterator<Npc> it = localNPCs.iterator(); it.hasNext();) {
			Npc n = it.next();
			if (!n.isActive() || !Config.SERVER_NPCS || n.isTeleporting()
					|| n.getPosition().getLongestDelta(player.getPosition()) > player.getViewingDistance()) {
				it.remove();
				segments.add(new RemoveCharacterSegment());
			} else {
				segments.add(new MovementSegment(n.getBlockSet(), n.getDirections()));
			}
		}
		int added = 0;
		CharacterRepository<Npc> repository = World.getWorld().getNpcRepository();
		for (Npc n : repository) {
			if (localNPCs.size() >= 255 || !Config.SERVER_NPCS) {
				break;
			} else {
				if (added >= NEW_NPCS_PER_CYCLE) {
					break;
				}
				if (n.getPosition().isWithinDistance(player.getPosition(), player.getViewingDistance())
						&& !localNPCs.contains(n)) {
					localNPCs.add(n);
					added++;
					blockSet = n.getBlockSet();
					segments.add(new AddNpcSegment(blockSet, n.getIndex(), n.getPosition(), n.getDefinition().getId()));
				}
			}
		}
		NPCSynchronizationEvent event = new NPCSynchronizationEvent(player.getPosition(), oldLocalPlayers, segments);
		player.send(event);
	}
}