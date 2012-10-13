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
	 * The amount.
	 */
	private final int amount;
	
	/**
	 * Creates the special event.
	 * @param bar The bar.
	 * @param amount The amount.
	 */
	public SpecialEvent(int bar, int amount) {
		this.bar = bar;
		this.amount = amount;
	}
	
	/**
	 * Gets the bar.
	 * @return The bar.
	 */
	public int getBar() {
		return bar;
	}
	
	/**
	 * Gets the amount.
	 * @return The amount.
	 */
	public int getAmount() {
		return amount;
	}

}
