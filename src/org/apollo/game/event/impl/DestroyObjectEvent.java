package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} which represents destroying objects in game.
 * @author Steve
 */
public final class DestroyObjectEvent extends CreateObjectEvent {

    /**
     * The object orientation.
     */
    private final int orient;

    /**
     * The object tile id.
     */
    private final int tile;

    /**
     * Creates a new second object action event.
     * @param orient The object orientation.
     * @param tile The object tile id.
     */
    public DestroyObjectEvent(int orient, int tile) {
	super(0);
	this.orient = orient;
	this.tile = tile;
    }

    /**
     * Gets the object orientation.
     * @return The object orientation.
     */
    public int getOrient() {
	return orient;
    }

    /**
     * Gets the object tile id.
     * @return The object tile id.
     */
    public int getTile() {
	return tile;
    }
}
