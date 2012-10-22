package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} which updates the special.
 * @author Steve
 */
public final class SpecialEvent extends Event {

	/**
	 * The bar.
	 */
	private final int bar;

	/**
	 * The enabled flag.
	 */
	private final boolean enabled;

	/**
	 * Creates the special event.
	 * @param enabled The enabled flag.
	 * @param bar The bar id.
	 */
	public SpecialEvent(boolean enabled, int bar) {
		this.enabled = enabled;
		this.bar = bar;
	}

	/**
	 * Gets the bar.
	 * @return The bar.
	 */
	public int getBar() {
		return bar;
	}

	/**
	 * Gets the enabled flag.
	 * @return The enabled flag.
	 */
	public boolean isEnabled() {
		return enabled;
	}

}
