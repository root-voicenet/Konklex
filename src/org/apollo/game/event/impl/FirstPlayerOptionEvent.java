package org.apollo.game.event.impl;

/**
 * An {@link Event} that handles the first player option event.
 * @author Steve
 */
public class FirstPlayerOptionEvent extends PlayerOptionEvent {

    /**
     * Create a new first player option event.
     * @param otherId The other player id.
     */
    public FirstPlayerOptionEvent(int otherId) {
	super(1, otherId);
    }
}
