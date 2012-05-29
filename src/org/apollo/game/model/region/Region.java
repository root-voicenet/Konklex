package org.apollo.game.model.region;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apollo.game.model.GameObject;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;

/**
 * Represents a single region.
 * @author Graham Edgecombe
 */
public class Region {

    /**
     * The region coordinates.
     */
    private RegionCoordinates coordinate;

    /**
     * A list of players in this region.
     */
    private List<Player> players = new LinkedList<Player>();

    /**
     * A list of NPCs in this region.
     */
    private List<Npc> npcs = new LinkedList<Npc>();

    /**
     * A list of objects in this region.
     */
    private List<GameObject> objects = new LinkedList<GameObject>();

    /**
     * A list of ground items in this region.
     */
    private List<GroundItem> items = new LinkedList<GroundItem>();

    /**
     * Creates a region.
     * @param coordinate The coordinate.
     */
    public Region(RegionCoordinates coordinate) {
	this.coordinate = coordinate;
    }

    /**
     * Adds a new item.
     * @param item The item to add.
     */
    public void addItem(GroundItem item) {
	synchronized (this) {
	    items.add(item);
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
     * Gets the region coordinates.
     * @return The region coordinates.
     */
    public RegionCoordinates getCoordinates() {
	return coordinate;
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
     * Gets the list of players.
     * @return The list of players.
     */
    public Collection<Player> getPlayers() {
	synchronized (this) {
	    return Collections.unmodifiableCollection(new LinkedList<Player>(players));
	}
    }

    /**
     * Removes an old ground item.
     * @param item The item to remove.
     */
    public void removeItem(GroundItem item) {
	synchronized (this) {
	    items.remove(item);
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
	synchronized (this) {
	    objects.remove(object);
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

}
