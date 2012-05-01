package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.Position;

/**
 * An {@link Event} that sends a dynamic map region that is constructed by using groups of 8*8 tiles.
 * @author Steve
 */
public final class ConstructMapRegionEvent extends Event {

	/**
	 * The absolute position.
	 */
	private final Position position;

	/**
	 * The flag for displaying the region.
	 */
	private final boolean display;

	/**
	 * Creates the new construct map region event.
	 * @param position The absolute position.
	 * @param display True to display the region, false if not.
	 */
	public ConstructMapRegionEvent(Position position, boolean display) {
		this.position = position;
		this.display = display;
	}

	/**
	 * Gets the absolute position.
	 * @return The absolute position.
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * The flag for displaying the region.
	 * @return True if displaying, false if not.
	 */
	public boolean isDisplayable() {
		return display;
	}
}
