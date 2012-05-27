package org.apollo.game.event.impl;

import org.apollo.game.event.Event;
import org.apollo.game.model.Position;

/**
 * An {@link Event} which represents a position change.
 * @author Steve
 */
public final class PositionEvent extends Event {

	/**
	 * The base.
	 */
	private final Position base;

	/**
	 * The position.
	 */
	private final Position position;

	/**
	 * Creates the event with the specified base and position.
	 * @param base The position base.
	 * @param position The position of the event.
	 */
	public PositionEvent(Position base, Position position) {
		this.base = base;
		this.position = position;
	}

	/**
	 * Gets the base.
	 * @return The base.
	 */
	public Position getBase() {
		return base;
	}

	/**
	 * Gets the position.
	 * @return The position.
	 */
	public Position getPosition() {
		return position;
	}
}