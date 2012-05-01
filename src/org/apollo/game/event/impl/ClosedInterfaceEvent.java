package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * Sent by the client when the current interface is closed.
 * @author Graham
 */
public final class ClosedInterfaceEvent extends Event {

	/**
	 * The interface that was closed.
	 */
	private final int closedInterface;

	/**
	 * Creates a closed interface event.
	 * @param closedInterface The interface that was closed.
	 */
	public ClosedInterfaceEvent(int closedInterface) {
		this.closedInterface = closedInterface;
	}

	/**
	 * Gets the interface that was closed.
	 * @return The interface that was closed.
	 */
	public int getClosedInterface() {
		return closedInterface;
	}
}
