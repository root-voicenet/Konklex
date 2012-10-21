package org.apollo.game.model.region;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.apollo.game.event.Event;
import org.apollo.game.event.impl.CreateGroundEvent;
import org.apollo.game.event.impl.CreateObjectEvent;
import org.apollo.game.event.impl.DestroyGroundEvent;
import org.apollo.game.event.impl.DestroyObjectEvent;
import org.apollo.game.event.impl.MapEvent;
import org.apollo.game.event.impl.RegionUpdateEvent;
import org.apollo.game.model.Character;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.def.ObjectDefinition;
import org.apollo.game.model.obj.StaticObject;

/**
 * Represents a single region.
 * @author Graham Edgecombe
 */
public final class Region {

	/**
	 * The region coordinates.
	 */
	private final RegionCoordinates coordinate;

	/**
	 * A map which links events to this region.
	 */
	private final Deque<Event> events = new ArrayDeque<Event>();

	/**
	 * A list of players in this region.
	 */
	private final List<Player> players = new LinkedList<Player>();

	/**
	 * A list of NPCs in this region.
	 */
	private final List<Npc> npcs = new LinkedList<Npc>();

	/**
	 * A list of objects in this region.
	 */
	private final List<GameObject> objects = new LinkedList<GameObject>();

	/**
	 * A list of ground items in this region.
	 */
	private final List<GroundItem> items = new LinkedList<GroundItem>();

	/**
	 * A list of static objects in this region.
	 */
	private final List<StaticObject> staticObjects = new LinkedList<StaticObject>();

	/**
	 * Creates a region.
	 * @param coordinate The coordinate.
	 */
	public Region(RegionCoordinates coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * Adds a new character.
	 * @param character The new character.
	 */
	public void addCharacter(Character character) {
		if (character.isControlling()) {
			addPlayer((Player) character);
		}
		else {
			addNpc((Npc) character);
		}
	}

	/**
	 * Adds a new event.
	 * @param event The event to add.
	 */
	public void addEvent(Event event) {
		synchronized (this) {
			events.add(event);
		}
	}

	/**
	 * Adds a new item.
	 * @param item The item to add.
	 */
	public void addItem(GroundItem item) {
		synchronized (this) {
			items.add(item);
			events.add(new CreateGroundEvent(item));
		}
	}

	/**
	 * Adds a new npc.
	 * @param npc The npc to add.
	 */
	public void addNpc(Npc npc) {
		synchronized (this) {
			npcs.add(npc);
		}
	}

	/**
	 * Adds a new object.
	 * @param object The object to add.
	 */
	public void addObject(GameObject object) {
		synchronized (this) {
			objects.add(object);
			events.add(new CreateObjectEvent(object));
		}
	}

	/**
	 * Adds a new player.
	 * @param player The player to add.
	 */
	public void addPlayer(Player player) {
		synchronized (this) {
			players.add(player);
		}
	}

	/**
	 * Adds a static object to this region.
	 * @param object The object to add.
	 */
	public void addStaticObject(StaticObject object) {
		if (object == null)
			return;
		synchronized (this) {
			staticObjects.add(object);
		}
	}

	/**
	 * Clears the events.
	 */
	public void clearEvents() {
		events.clear();
	}

	/**
	 * Gets the list of characters.
	 * @return The list of characters.
	 */
	public List<Character> getCharacters() {
		List<Character> characters = new ArrayList<Character>(players.size() + npcs.size());
		synchronized (this) {
			characters.addAll(players);
			characters.addAll(npcs);
			return Collections.unmodifiableList(characters);
		}
	}

	/**
	 * Gets the region coordinates.
	 * @return The region coordinates.
	 */
	public RegionCoordinates getCoordinates() {
		return coordinate;
	}

	/**
	 * Gets the event at the position.
	 * @param position The position.
	 * @return The event, if any, at the position.
	 */
	private Event getEvent(Position position) {
		Event event = null;
		synchronized (this) {
			for (final Event n_event : events)
				if (n_event instanceof MapEvent) {
					final MapEvent map = (MapEvent) n_event;
					if (map.getPosition().equals(position)) {
						event = n_event;
					}
				}
		}
		return event;
	}

	/**
	 * Gets the list of events.
	 * @return The list of events.
	 */
	public Collection<Event> getEvents() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<Event>(events));
		}
	}

	/**
	 * Gets the list of objects.
	 * @return The list of objects.
	 */
	public Collection<GameObject> getGameObjects() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<GameObject>(objects));
		}
	}

	/**
	 * Gets a list of ground items.
	 * @return The list of ground items.
	 */
	public Collection<GroundItem> getGroundItems() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<GroundItem>(items));
		}
	}

	/**
	 * Gets the list of NPCs.
	 * @return The list of NPCs.
	 */
	public Collection<Npc> getNpcs() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<Npc>(npcs));
		}
	}

	/**
	 * Gets the object at the position.
	 * @param position The position.
	 * @return The game object, if any, at the position.
	 */
	private GameObject getObject(Position position) {
		GameObject returnz = null;
		for (final GameObject object : objects)
			if (object.getLocation().equals(position)) {
				returnz = object;
			}
		return returnz;
	}

	/**
	 * Gets the list of objects.
	 * @return The list of objects.
	 */
	public Collection<GameObject> getObjects() {
		List<GameObject> gameObjects = new ArrayList<GameObject>();
		synchronized (this) {
			for (StaticObject object : staticObjects) {
				ObjectDefinition definition = ObjectDefinition.forId(object.getId());
				GameObject go = new GameObject(definition, object.getPosition(), object.getType(), object.getRotation());
				gameObjects.add(go);
			}
			gameObjects.addAll(objects);
			return Collections.unmodifiableCollection(new LinkedList<GameObject>(gameObjects));
		}
	}

	/**
	 * Gets the list of players.
	 * @return The list of players.
	 */
	public Collection<Player> getPlayers() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<Player>(players));
		}
	}

	/**
	 * Gets the list of static objects.
	 * @return The list of statuc objects.
	 */
	public Collection<StaticObject> getStaticObjects() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<StaticObject>(staticObjects));
		}
	}

	/**
	 * Removes a old character.
	 * @param character The character to remove.
	 */
	public void removeCharacter(Character character) {
		if (character.isControlling()) {
			removePlayer((Player) character);
		}
		else {
			removeNpc((Npc) character);
		}
	}

	/**
	 * Removes an old event.
	 * @param event The event to remove.
	 */
	public void removeEvent(Event event) {
		synchronized (this) {
			events.remove(event);
		}
	}

	/**
	 * Removes an old ground item.
	 * @param item The item to remove.
	 */
	public void removeItem(GroundItem item) {
		final Event event = getEvent(item.getPosition());
		synchronized (this) {
			events.remove(event);
			items.remove(item);
			sendEvent(new DestroyGroundEvent(item));
		}
	}

	/**
	 * Removes an old npc.
	 * @param npc The npc to remove.
	 */
	public void removeNpc(Npc npc) {
		synchronized (this) {
			npcs.remove(npc);
		}
	}

	/**
	 * Removes an old object.
	 * @param object The object to remove.
	 */
	public void removeObject(GameObject object) {
		final Event event = getEvent(object.getLocation());
		synchronized (this) {
			events.remove(event);
			objects.remove(object);
			sendEvent(new DestroyObjectEvent(object));
		}
	}

	/**
	 * Removes an old player.
	 * @param player The player to remove.
	 */
	public void removePlayer(Player player) {
		synchronized (this) {
			players.remove(player);
		}
	}

	/**
	 * Replaces an object at the position.
	 * @param position The position of the old object.
	 * @param object The object to replace with.
	 */
	public void replaceObject(Position position, GameObject object) {
		final GameObject replace = getObject(position);
		if (replace != null) {
			synchronized (this) {
				removeObject(replace);
				addObject(object);
			}
		}
		else {
			addObject(object);
		}
	}

	/**
	 * Sends an event.
	 * @param event The event to send.
	 */
	public void sendEvent(Event event) {
		for (Player player : players) {
			final int REGION_SIZE = RegionManager.REGION_SIZE;
			final RegionCoordinates coordinates = player.getRegion().getCoordinates();
			final Position position = new Position(coordinates.getX() * REGION_SIZE, coordinates.getY() * REGION_SIZE);
			player.send(new RegionUpdateEvent(player.getLastKnownRegion(), position, event));
		}
	}

}
