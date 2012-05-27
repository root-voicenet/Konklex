package org.apollo.game.model.obj;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.def.StaticObjectDefinition;

/**
 * Represents static objects in the game world.
 * @author Steve
 */
public final class WorldObject {

	/**
	 * The list of objects at the correct position.
	 */
	private final Map<Position, StaticObjectDefinition> objects = new HashMap<Position, StaticObjectDefinition>();

	/**
	 * Adds a object to the list of global objects.
	 * @param definition The object definition.
	 */
	public void add(StaticObjectDefinition definition) {
		if (!objects.containsKey(definition.getPosition())) {
			definition.setType(ObjectEnum.ADD);
			objects.put(definition.getPosition(), definition);
			for (Player player : World.getWorld().getPlayerRepository()) {
				player.getObjectSet().add(definition);
			}
		}
	}

	/**
	 * Gets the object for the current position.
	 * @param position The position.
	 * @return The object that is at that position.
	 */
	public StaticObjectDefinition getObject(Position position) {
		return objects.get(position);
	}

	/**
	 * Gets the global objects.
	 * @return The global objects.
	 */
	public Map<Position, StaticObjectDefinition> getObjects() {
		return objects;
	}

	/**
	 * Removes a object from the list.
	 * @param definition The object definition.
	 */
	public void remove(StaticObjectDefinition definition) {
		if (objects.containsKey(definition.getPosition())) {
			definition.setType(ObjectEnum.REMOVE);
			objects.remove(definition.getPosition());
			for (Player player : World.getWorld().getPlayerRepository()) {
				player.getObjectSet().remove(
						new StaticObjectDefinition(definition.getPosition(), -1, definition.getOrient(), definition
								.getTile()));
			}
		}
	}

	/**
	 * Replaces a object from the list.
	 * @param definition The object definition.
	 */
	public void replace(StaticObjectDefinition definition) {
		if (objects.containsKey(definition.getPosition())) {
			definition.setType(ObjectEnum.REPLACE);
			objects.remove(definition.getPosition());
			objects.put(definition.getPosition(), definition);
			for (Player player : World.getWorld().getPlayerRepository()) {
				player.getObjectSet().replace(definition);
			}
		}
	}
}
