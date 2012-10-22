package org.apollo.game.event.impl;

import org.apollo.game.model.GroundItem;

/**
 * An {@link GroundEvent} that creates a ground item.
 * @author Steve
 */
public final class CreateGroundEvent extends GroundEvent {

	/**
	 * Creates a create ground event.
	 * @param groundItem The ground item to create.
	 */
	public CreateGroundEvent(GroundItem groundItem) {
		super(groundItem.getPosition(), groundItem);
	}

	/**
	 * Gets the event id.
	 * @return The event id.
	 */
	@Override
	public int getEventId() {
		return 2;
	}

}
