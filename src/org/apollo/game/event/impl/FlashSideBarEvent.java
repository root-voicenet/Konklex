package org.apollo.game.event.impl;

import org.apollo.game.event.Event;

/**
 * An {@link Event} for the {@link FlashSideBarEventEncoder}
 * @author Solid Snake
 */
public class FlashSideBarEvent extends Event {

	/**
	 * The sidebar which will be flashing.
	 */
	private int sideBar;

	/**
	 * Create a new flash sidebar event.
	 * @param sidebar The sidebar to flash.
	 */
	public FlashSideBarEvent(int sidebar) {
		this.sideBar = sidebar;
	}

	/**
	 * Gets the sidebar value.
	 * @return The sidebar value.
	 */
	public int getSideBar() {
		return sideBar;
	}
}