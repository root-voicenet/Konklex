package org.apollo.game.model;

import org.apollo.game.event.Event;
import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.game.model.def.NpcDefinition;
import org.apollo.game.model.region.Region;
import org.apollo.game.scheduling.impl.RandomizedNpcWalkingTask;
import org.apollo.game.sync.block.SynchronizationBlock;

/**
 * A {@link Npc} is a {@link Character} which is computer-controlled (Non-Player
 * Character).
 * @author Chris Fletcher
 */
public final class Npc extends Character {

    /**
     * The default position of this NPC.
     */
    private static final Position DEFAULT_POSITION = new Position(0, 0);

    /**
     * The NPC id.
     */
    private final int id;

    /**
     * Creates a new NPC.
     * @param id The NPC id.
     */
    public Npc(int id) {
	this(id, DEFAULT_POSITION);
    }

    /**
     * Creates a new NPC.
     * @param id The NPC id.
     * @param position The position to place the NPC at.
     */
    public Npc(int id, Position position) {
	super(position);
	this.id = id;
	this.init();
    }

    /**
     * Gets the definition of this NPC.
     * @return The definition.
     */
    public NpcDefinition getDefinition() {
	return NpcDefinition.forId(id);
    }

    @Override
    public int getHealth() {
	// TODO Auto-generated method stub
	return 1;
    }

    @Override
    public int getHealthMax() {
	// TODO Auto-generated method stub
	return 1;
    }

    /**
     * Gets the NPC id.
     * @return The id.
     */
    public int getId() {
	return id;
    }

    /**
     * Initializes this npc.
     */
    public void init() {
	World.getWorld().schedule(new RandomizedNpcWalkingTask(this));
    }

    @Override
    public void send(Event event) {
	if (event instanceof ServerMessageEvent) {
	    final String message = ((ServerMessageEvent) event).getMessage();
	    getBlockSet().add(SynchronizationBlock.createForceChatBlock(message));
	}
    }

    @Override
    public void setPosition(Position position) {
	final Region region = World.getWorld().getRegionManager().getRegionByLocation(position);
	if (getRegion() != null) {
	    if (getRegion() != region) {
		getRegion().removeNpc(this);
		setRegion(region);
		region.addNpc(this);
	    }
	} else {
	    setRegion(region);
	    region.addNpc(this);
	}
	super.setPosition(position);
    }

    @Override
    public String toString() {
	return Npc.class.getName() + " [id=" + id + "]";
    }

}