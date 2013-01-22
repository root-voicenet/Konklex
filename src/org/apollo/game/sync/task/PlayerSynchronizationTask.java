package org.apollo.game.sync.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apollo.game.event.Event;
import org.apollo.game.event.impl.CreateGroundEvent;
import org.apollo.game.event.impl.NpcSynchronizationEvent;
import org.apollo.game.event.impl.PlayerSynchronizationEvent;
import org.apollo.game.event.impl.RegionUpdateEvent;
import org.apollo.game.model.Config;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.region.Region;
import org.apollo.game.model.region.RegionCoordinates;
import org.apollo.game.model.region.RegionManager;
import org.apollo.game.sync.block.AppearanceBlock;
import org.apollo.game.sync.block.ChatBlock;
import org.apollo.game.sync.block.SynchronizationBlock;
import org.apollo.game.sync.block.SynchronizationBlockSet;
import org.apollo.game.sync.seg.AddCharacterSegment;
import org.apollo.game.sync.seg.AddNpcSegment;
import org.apollo.game.sync.seg.MovementSegment;
import org.apollo.game.sync.seg.RemoveCharacterSegment;
import org.apollo.game.sync.seg.SynchronizationSegment;
import org.apollo.game.sync.seg.TeleportSegment;

/**
 * A {@link SynchronizationTask} which synchronizes the specified {@link Player} .
 * @author Graham
 */
public final class PlayerSynchronizationTask extends SynchronizationTask {

	/**
	 * The maximum number of players to load per cycle. This prevents the update packet from becoming too large (the
	 * client uses a 5000 byte buffer) and also stops old spec PCs from crashing when they login or teleport.
	 */
	private static final int NEW_PLAYERS_PER_CYCLE = 20;
	
	/**
	 * The maximum number of npcs to load per cycle. This prevents the update packet from becoming too large (the
	 * client uses a 5000 byte buffer).
	 */
	private static final int NEW_NPCS_PER_CYCLE = 20;
	
	/**
	 * The maximum number of events to load per region cycle. This prevents the update packet from becoming too large (the
	 * client uses a 5000 byte buffer).
	 */
	private static final int NEW_EVENTS_PER_REGION_CYCLE = 20;

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
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		updateRegion(player);
		updatePlayers(player);
		updateNpcs(player);
	}
	
	private void updateRegion(Player player) {
		if (player.isRegionUpdateRequired()) {
			final List<Event> localEvents = player.getLocalEventList();
			final Map<Region, List<Event>> newLocalEvents = World.getWorld().getRegionManager().getLocalEvents(player);
			
			for (final Entry<Region, List<Event>> regionEntry : newLocalEvents.entrySet()) {
				int added = 0;
				final Region region = regionEntry.getKey();
				final List<Event> events = new ArrayList<Event>();
				final List<Event> regionEvents = regionEntry.getValue();
	
				for (Event event : regionEvents) {
					if (added >= NEW_EVENTS_PER_REGION_CYCLE) {
						break;
					}
	
					if (!localEvents.contains(event)) {
						if (event.getEventId() == 2) {
							final CreateGroundEvent ground = (CreateGroundEvent) event;
							final GroundItem item = ground.getGroundItem();
							if (item.getControllerName().equals("") || item.getControllerName().equals(player.getName()) || item.getPulses() == 0) {
								localEvents.add(event);
								events.add(event);
								added++;
							}
						}
						else if (localEvents.add(event)) {
							events.add(event);
							added++;
						}
					}
				}
				
				if (added > 0) {
					final int REGION_SIZE = RegionManager.REGION_SIZE;
					final RegionCoordinates coordinates = region.getCoordinates();
					final Position position = new Position(coordinates.getX() * REGION_SIZE, coordinates.getY() * REGION_SIZE);
					final Position playerPos = player.getLastKnownRegion() != null ? player.getLastKnownRegion() : player.getPosition();
					player.send(new RegionUpdateEvent(playerPos, position, events));
				}
			}
		}
	}

	/**
	 * Updates the players.
	 * @param player The player to receive the update.
	 */
	private void updatePlayers(Player player) {
		final Position lastKnownRegion = player.getLastKnownRegion();
		final boolean regionChanged = player.hasRegionChanged();
		SynchronizationSegment segment;
		SynchronizationBlockSet blockSet = player.getBlockSet();
		if (blockSet.contains(ChatBlock.class)) {
			blockSet = blockSet.clone();
			blockSet.remove(ChatBlock.class);
		}
		if (player.isTeleporting() || player.hasRegionChanged()) {
			segment = new TeleportSegment(blockSet, player.getPosition());
		}
		else {
			segment = new MovementSegment(blockSet, player.getDirections());
		}
		final List<Player> localPlayers = player.getLocalPlayerList();
		final int oldLocalPlayers = localPlayers.size();
		final List<SynchronizationSegment> segments = new ArrayList<SynchronizationSegment>();

		int added = 0;
		final Collection<Player> repository = World.getWorld().getRegionManager().getLocalPlayers(player);

		for (final Iterator<Player> it = localPlayers.iterator(); it.hasNext();) {
			final Player p = it.next();
			final boolean check = p.getPosition().getHeight() == player.getPosition().getHeight();
			if (!p.isActive() || p.isTeleporting() || !check
					|| p.getPosition().getLongestDelta(player.getPosition()) > player.getViewingDistance()
					|| p.isHidden()) {
				it.remove();
				segments.add(new RemoveCharacterSegment());
			}
			else {
				segments.add(new MovementSegment(p.getBlockSet(), p.getDirections()));
			}
		}

		for (final Player p : repository) {
			if (localPlayers.size() >= 255) {
				player.flagExcessivePlayers();
				break;
			}
			else if (added >= NEW_PLAYERS_PER_CYCLE) {
				break;
			}
			if (p != player && !localPlayers.contains(p)) {
				if (!p.isHidden() && localPlayers.add(p)) {
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
		}
		final PlayerSynchronizationEvent event = new PlayerSynchronizationEvent(lastKnownRegion, player.getPosition(),
				regionChanged, segment, oldLocalPlayers, segments);
		player.send(event);
	}
	
	/**
	 * Updates the npcs.
	 * @param player The player to receive the update.
	 */
	private void updateNpcs(Player player) {
		SynchronizationBlockSet blockSet = player.getBlockSet();
		final List<Npc> localNPCs = player.getLocalNpcList();
		final int oldLocalPlayers = localNPCs.size();
		final List<SynchronizationSegment> segments = new ArrayList<SynchronizationSegment>();

		int added = 0;
		final Collection<Npc> repository = World.getWorld().getRegionManager().getLocalNpcs(player);

		for (final Iterator<Npc> it = localNPCs.iterator(); it.hasNext();) {
			final Npc n = it.next();
			final boolean check = n.getPosition().getHeight() == player.getPosition().getHeight();
			if (!n.isActive() || n.isTeleporting() || !check
					|| n.getPosition().getLongestDelta(player.getPosition()) > player.getViewingDistance()) {
				it.remove();
				segments.add(new RemoveCharacterSegment());
			}
			else {
				segments.add(new MovementSegment(n.getBlockSet(), n.getDirections()));
			}
		}

		for (final Npc n : repository)
			if (localNPCs.size() >= 255 || !Config.SERVER_NPCS) {
				break;
			}
			else {
				if (added >= NEW_NPCS_PER_CYCLE) {
					break;
				}
				if (!localNPCs.contains(n) && n.isActive()) {
					localNPCs.add(n);
					added++;
					blockSet = n.getBlockSet();
					if (n.getFace() > 1) {
						blockSet = blockSet.clone();
						blockSet.add(SynchronizationBlock.createTurnToPositionBlock(transformNpcFacing(n)));
					}
					segments.add(new AddNpcSegment(blockSet, n.getIndex(), n.getPosition(), n.getDefinition().getId()));
				}
			}

		final NpcSynchronizationEvent event = new NpcSynchronizationEvent(player.getPosition(), oldLocalPlayers,
				segments);
		player.send(event);
	}
	
	/**
	 * Gets the facing position.
	 * @param n The npc.
	 * @return The facing position.
	 */
	private Position transformNpcFacing(Npc n) {
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
