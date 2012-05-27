package org.apollo.game.model.region;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apollo.game.model.Npc;
import org.apollo.game.model.def.StaticObjectDefinition;

/**
 * Represents an 8x8 region.
 * @author Graham
 */
public final class Region {

	/**
	 * The width and height of the region in tiles.
	 */
	public static final int REGION_SIZE = 8;

	/**
	 * The region coordinate.
	 */
	private RegionCoordinates coordinate;

	/**
	 * A list of NPCs in the region.
	 */
	private List<Npc> npcs = new LinkedList<Npc>();

	/**
	 * A list of objects in the region.
	 */
	private List<StaticObjectDefinition> objects = new LinkedList<StaticObjectDefinition>();

	/**
	 * Define a new region.
	 * @param coordinate The region coordinate.
	 */
	public Region(RegionCoordinates coordinate) {
		this.setCoordinate(coordinate);
	}

	/**
	 * Adds an NPC.
	 * @param npc The NPC to add.
	 */
	public void addNPC(Npc npc) {
		synchronized (this) {
			npcs.add(npc);
		}
	}

	/**
	 * Adds a object.
	 * @param object The object definition.
	 */
	public void addObject(StaticObjectDefinition object) {
		synchronized (this) {
			objects.add(object);
		}
	}

	/**
	 * To avoid hints.
	 * @return THe coordinate
	 */
	public RegionCoordinates getCoordinate() {
		return coordinate;
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
	 * Gets the list of Objects.
	 * @return The list of Objects.
	 */
	public Collection<StaticObjectDefinition> getObjects() {
		synchronized (this) {
			return Collections.unmodifiableCollection(new LinkedList<StaticObjectDefinition>(objects));
		}
	}

	/**
	 * Removes an NPC.
	 * @param npc The NPC to remove.
	 */
	public void removeNPC(Npc npc) {
		synchronized (this) {
			npcs.remove(npc);
		}
	}

	/**
	 * Removes an object.
	 * @param object The object to remove.
	 */
	public void removeObject(StaticObjectDefinition object) {
		synchronized (this) {
			objects.remove(object);
		}
	}

	/**
	 * To avoid hints.
	 * @param coordinate The coordinate.
	 */
	public void setCoordinate(RegionCoordinates coordinate) {
		this.coordinate = coordinate;
	}
}
