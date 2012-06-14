package org.apollo.game.sync.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apollo.game.event.impl.PlayerSynchronizationEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;
import org.apollo.game.sync.block.AppearanceBlock;
import org.apollo.game.sync.block.ChatBlock;
import org.apollo.game.sync.block.ForceMovementBlock;
import org.apollo.game.sync.block.SynchronizationBlock;
import org.apollo.game.sync.block.SynchronizationBlockSet;
import org.apollo.game.sync.seg.AddCharacterSegment;
import org.apollo.game.sync.seg.MovementSegment;
import org.apollo.game.sync.seg.RemoveCharacterSegment;
import org.apollo.game.sync.seg.SynchronizationSegment;
import org.apollo.game.sync.seg.TeleportSegment;

/**
 * A {@link SynchronizationTask} which synchronizes the specified {@link Player}
 * .
 * @author Graham
 */
public final class PlayerSynchronizationTask extends SynchronizationTask {

    /**
     * The maximum number of players to load per cycle. This prevents the update
     * packet from becoming too large (the client uses a 5000 byte buffer) and
     * also stops old spec PCs from crashing when they login or teleport.
     */
    private static final int NEW_PLAYERS_PER_CYCLE = 20;

    /**
     * The player.
     */
    private final Player player;

    /**
     * Creates the {@link PlayerSynchronizationTask} for the specified player.
     * @param player The player.
     */
    public PlayerSynchronizationTask(Player player) {
	this.player = player;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
	final Position lastKnownRegion = player.getLastKnownRegion();
	final boolean regionChanged = player.hasRegionChanged();
	SynchronizationSegment segment;
	SynchronizationBlockSet blockSet = player.getBlockSet();
	if (blockSet.contains(ChatBlock.class)) {
	    blockSet = blockSet.clone();
	    blockSet.remove(ChatBlock.class);
	}
	if (player.isTeleporting()) {
	    segment = new TeleportSegment(blockSet, player.getPosition());
	} else {
	    segment = new MovementSegment(blockSet, player.getDirections());
	}
	final List<Player> localPlayers = player.getLocalPlayerList();
	final int oldLocalPlayers = localPlayers.size();
	final List<SynchronizationSegment> segments = new ArrayList<SynchronizationSegment>();
	for (final Iterator<Player> it = localPlayers.iterator(); it.hasNext();) {
	    final Player p = it.next();
	    if (!p.isActive() || p.isTeleporting()
		    || p.getPosition().getLongestDelta(player.getPosition()) > player.getViewingDistance()) {
		it.remove();
		segments.add(new RemoveCharacterSegment());
	    } else {
		segments.add(new MovementSegment(p.getBlockSet(), p.getDirections()));
	    }
	}
	int added = 0;
	final Collection<Player> repository = World.getWorld().getRegionManager().getLocalPlayers(player);
	for (final Player p : repository) {
	    if (localPlayers.size() >= 255) {
		player.flagExcessivePlayers();
		break;
	    } else if (added >= NEW_PLAYERS_PER_CYCLE) {
		break;
	    }
	    if (p != player && !localPlayers.contains(p)) {
		localPlayers.add(p);
		added++;
		blockSet = p.getBlockSet();
		if (!blockSet.contains(AppearanceBlock.class)) {
		    // TODO check if client has cached appearance
		    blockSet = blockSet.clone();
		    blockSet.add(SynchronizationBlock.createAppearanceBlock(p));
		}
		segments.add(new AddCharacterSegment(blockSet, p.getIndex(), p.getPosition()));
	    }
	}
	final PlayerSynchronizationEvent event = new PlayerSynchronizationEvent(lastKnownRegion, player.getPosition(),
		regionChanged, segment, oldLocalPlayers, segments);
	player.send(event);
	if (blockSet.contains(ForceMovementBlock.class)) {
	    final ForceMovementBlock block = blockSet.get(ForceMovementBlock.class);
	    int speed = block.getFirstSpeed() + block.getSecondSpeed() + 600;
	    World.getWorld().schedule(new ScheduledTask(speed, true) {

		@Override
		public void execute() {
		    // player.sendMessage("SET: " + block.getPosition());
		    // player.teleport(block.getPosition(), false);
		    stop();
		}
	    });
	}
    }
}
