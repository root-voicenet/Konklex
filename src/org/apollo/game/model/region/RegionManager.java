package org.apollo.game.model.region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apollo.game.event.Event;
import org.apollo.game.model.Character;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.obj.StaticObject;

/**
 * Manages the world regions.
 * @author Graham Edgecombe
 */
public final class RegionManager {

	/**
	 * The region size.
	 */
	public static final int REGION_SIZE = 8;

	/**
	 * The lower bound that splits the region in half.
	 */
	@SuppressWarnings("unused")
	private static final int LOWER_BOUND = REGION_SIZE / 2 - 1;

	/**
	 * The active (loaded) region map.
	 */
	private final Map<RegionCoordinates, Region> activeRegions = new HashMap<RegionCoordinates, Region>();

	/**
	 * Gets the game object.
	 * @param player The player.
	 * @param position The position.
	 * @return The game object.
	 */
	public GameObject getGameObject(Player player, Position position) {
		final Collection<GameObject> collection = World.getWorld().getRegionManager().getLocalObjects(player);
		for (final GameObject gameObject : collection)
			if (gameObject.getLocation().equals(position) && player.getPosition().equals(gameObject.getLocation()))
				return gameObject;
		return null;
	}

	/**
	 * Gets the ground item.
	 * @param player The player.
	 * @param position The position.
	 * @param item The item.
	 * @return The ground item.
	 */
	public GroundItem getGroundItem(Player player, Position position, int item) {
		final Collection<GroundItem> collection = World.getWorld().getRegionManager().getLocalGroundItems(player);
		for (final GroundItem groundItem : collection)
			if (groundItem.getPosition().equals(position) && player.getPosition().equals(groundItem.getPosition()))
				if (groundItem.getItem().getId() == item)
					if (groundItem.getControllerName().equals("null")
							|| groundItem.getControllerName().equalsIgnoreCase(player.getName())
							|| groundItem.getPulses() == 0)
						return groundItem;
		return null;
	}

	/**
	 * Gets the local {@link Event}'s around an character.
	 * @param character The character.
	 * @return The collection of local {@link Event}'s.
	 */
	public Collection<Event> getLocalEvents(Character character) {
		final Region region = getRegionByLocation(character.getPosition());
		return Collections.unmodifiableCollection(region.getEvents());
	}

	/**
	 * Gets the local {@link GroundItem}'s around an character.
	 * @param character The character.
	 * @return The collection of local {@link GroundItem}'s.
	 */
	public Collection<GroundItem> getLocalGroundItems(Character character) {
		final List<GroundItem> localItems = new ArrayList<GroundItem>();
		final Region[] regions = getSurroundingRegions(character.getPosition());
		final int distance = Position.MAX_DISTANCE;
		for (final Region region : regions) {
			for (final GroundItem item : region.getGroundItems())
				if (item.getPosition().getHeight() == character.getPosition().getHeight())
					if (item.getPosition().getDistance(character.getPosition()) <= distance) {
						localItems.add(item);
					}
		}
		return Collections.unmodifiableCollection(localItems);
	}

	/**
	 * Gets the local npc's around an character.
	 * @param character The character.
	 * @return The collection of local npc's.
	 */
	public Collection<Npc> getLocalNpcs(Character character) {
		final List<Npc> localPlayers = new LinkedList<Npc>();
		final Region[] regions = getSurroundingRegions(character.getPosition());
		final int distance = Position.MAX_DISTANCE;
		for (final Region region : regions) {
			for (final Npc npc : region.getNpcs())
				if (npc.getPosition().getHeight() == character.getPosition().getHeight())
					if (npc.getPosition().getDistance(character.getPosition()) <= distance) {
						localPlayers.add(npc);
					}
		}
		return Collections.unmodifiableCollection(localPlayers);
	}

	/**
	 * Gets the local {@link GameObject}'s around an character.
	 * @param character The character.
	 * @return The collection of local {@link GameObject}'s.
	 */
	public Collection<GameObject> getLocalObjects(Character character) {
		final List<GameObject> localObjects = new ArrayList<GameObject>();
		final Region[] regions = getSurroundingRegions(character.getPosition());
		final int distance = Position.MAX_DISTANCE;
		for (final Region region : regions) {
			for (final GameObject object : region.getGameObjects())
				if (object.getLocation().getHeight() == character.getPosition().getHeight())
					if (object.getLocation().getDistance(character.getPosition()) <= distance) {
						localObjects.add(object);
					}
		}
		return Collections.unmodifiableCollection(localObjects);
	}

	/**
	 * Gets the local players around an character.
	 * @param character The character.
	 * @return The collection of local players.
	 */
	public Collection<Player> getLocalPlayers(Character character) {
		final List<Player> localPlayers = new LinkedList<Player>();
		final Region[] regions = getSurroundingRegions(character.getPosition());
		final int distance = Position.MAX_DISTANCE;
		for (final Region region : regions) {
			for (final Player player : region.getPlayers())
				if (player.getPosition().getHeight() == character.getPosition().getHeight())
					if (player.getPosition().getDistance(character.getPosition()) <= distance) {
						localPlayers.add(player);
					}
		}
		return Collections.unmodifiableCollection(localPlayers);
	}

	/**
	 * Gets the local {@link StaticObject}'s around an character.
	 * @param character The character.
	 * @return The collection of local {@link StaticObject}'s.
	 */
	public Collection<StaticObject> getLocalStaticObjects(Character character) {
		final List<StaticObject> localStaticObjects = new ArrayList<StaticObject>();
		final Region[] regions = getSurroundingRegions(character.getPosition());
		final int distance = Position.MAX_DISTANCE;
		for (final Region region : regions) {
			for (final StaticObject object : region.getStaticObjects())
				if (object.getPosition().getHeight() == character.getPosition().getHeight())
					if (object.getPosition().getDistance(character.getPosition()) <= distance) {
						localStaticObjects.add(object);
					}
		}
		return Collections.unmodifiableCollection(localStaticObjects);
	}

	/**
	 * Gets a region by its x and y coordinates.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The region.
	 */
	public Region getRegion(int x, int y) {
		final RegionCoordinates key = new RegionCoordinates(x, y);
		Region region = activeRegions.get(key);
		if (region == null) {
			region = new Region(key);
			activeRegions.put(key, region);
		}
		return region;
	}

	/**
	 * Gets a region by location.
	 * @param location The location.
	 * @return The region.
	 */
	public Region getRegionByLocation(Position location) {
		return getRegion(location.getX() / REGION_SIZE, location.getY() / REGION_SIZE);
	}

	/**
	 * Gets the regions surrounding a location.
	 * @param location The location.
	 * @return The regions surrounding the location.
	 */
	public Region[] getSurroundingRegions(Position location) {
		final int regionX = location.getX() / REGION_SIZE;
		final int regionY = location.getY() / REGION_SIZE;

		final Region[] surrounding = new Region[9];
		surrounding[0] = getRegion(regionX, regionY);
		surrounding[1] = getRegion(regionX - 1, regionY - 1);
		surrounding[2] = getRegion(regionX + 1, regionY + 1);
		surrounding[3] = getRegion(regionX - 1, regionY);
		surrounding[4] = getRegion(regionX, regionY - 1);
		surrounding[5] = getRegion(regionX + 1, regionY);
		surrounding[6] = getRegion(regionX, regionY + 1);
		surrounding[7] = getRegion(regionX - 1, regionY + 1);
		surrounding[8] = getRegion(regionX + 1, regionY - 1);

		return surrounding;
	}

	/**
	 * Gets the size of the regions.
	 * @return The size.
	 */
	public int size() {
		return activeRegions.size();
	}

}
