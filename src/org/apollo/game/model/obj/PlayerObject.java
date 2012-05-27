package org.apollo.game.model.obj;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apollo.game.event.impl.DestroyObjectEvent;
import org.apollo.game.event.impl.DisplayObjectEvent;
import org.apollo.game.event.impl.PositionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.def.StaticObjectDefinition;

/**
 * Represents static objects in the game world.
 * @author Steve
 */
public final class PlayerObject {

    /**
     * The player that this class belongs too.
     */
    private final Player player;

    /**
     * The list of objects at the correct position.
     */
    private final Map<Position, StaticObjectDefinition> objects = new HashMap<Position, StaticObjectDefinition>();

    /**
     * Create a new static object list for the player.
     * @param player The class this player belongs too.
     */
    public PlayerObject(Player player) {
	this.player = player;
	init();
    }

    /**
     * Adds a object to the list.
     * @param definition The object definition.
     */
    public void add(StaticObjectDefinition definition) {
	definition.setType(ObjectEnum.ADD);
	if (!objects.containsKey(definition.getPosition()))
	    objects.put(definition.getPosition(), definition);
	else {
	    final StaticObjectDefinition queuable = objects.get(definition.getPosition());
	    if (!queuable.getType().equals(ObjectEnum.ADD)) {
		objects.remove(definition.getPosition());
		objects.put(definition.getPosition(), definition);
	    }
	}
	process();
    }

    /**
     * Initializes the class, adding all the global objects to the player list.
     */
    private void init() {
	objects.putAll(World.getWorld().getObjectManager().getObjects());
    }

    /**
     * Run threw the object queue, sending the object if the player is close
     * enough.
     */
    public void process() {
	synchronized (objects) {
	    for (final Entry<Position, StaticObjectDefinition> objects : this.objects.entrySet()) {
		final Position def = objects.getKey();
		final StaticObjectDefinition object = objects.getValue();
		if (player.getPosition().isWithinDistance(object.getPosition(), Position.MAX_DISTANCE)
			&& player.getPosition().getHeight() == object.getPosition().getHeight()) {
		    player.send(new PositionEvent(player.getPosition(), object.getPosition()));
		    if (object.getType().equals(ObjectEnum.ADD))
			player.send(new DisplayObjectEvent(object.getObject(), object.getOrient(), object.getTile()));
		    else if (object.getType().equals(ObjectEnum.REMOVE))
			player.send(new DestroyObjectEvent(object.getOrient(), object.getTile()));
		    else if (object.getType().equals(ObjectEnum.REPLACE)) {
			player.send(new DestroyObjectEvent(object.getOrient(), object.getTile()));
			player.send(new DisplayObjectEvent(object.getObject(), object.getOrient(), object.getTile()));
		    }
		    this.objects.remove(def);
		}
	    }
	}
    }

    /**
     * Removes a object from the list.
     * @param definition The object definition.
     */
    public void remove(StaticObjectDefinition definition) {
	definition.setType(ObjectEnum.REMOVE);
	if (!objects.containsKey(definition.getPosition()))
	    objects.put(definition.getPosition(), definition);
	else {
	    final StaticObjectDefinition queuable = objects.get(definition.getPosition());
	    if (!queuable.getType().equals(ObjectEnum.REMOVE)) {
		objects.remove(definition.getPosition());
		objects.put(definition.getPosition(), definition);
	    }
	}
	process();
    }

    /**
     * Replaces a object from the list.
     * @param definition The object definition.
     */
    public void replace(StaticObjectDefinition definition) {
	definition.setType(ObjectEnum.REPLACE);
	if (!objects.containsKey(definition.getPosition()))
	    objects.put(definition.getPosition(), definition);
	else {
	    final StaticObjectDefinition queuable = objects.get(definition.getPosition());
	    if (!queuable.getType().equals(ObjectEnum.REPLACE)) {
		objects.remove(definition.getPosition());
		objects.put(definition.getPosition(), definition);
	    }
	}
	process();
    }
}
