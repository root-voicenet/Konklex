package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} which represents destroying objects in game.
 * @author Steve
 */
public class DestroyObjectEvent extends CreateObjectEvent {

	/**
	 * The object orentation.
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
	 * Return the object orientation.
	 * @return {@link Integer} The object orientation.
	 */
	public int getOrient() {
		return orient;
	}

	/**
	 * Return the object tile id.
	 * @return {@link Integer} The object tile id.
	 */
	public int getTile() {
		return tile;
	}
}
