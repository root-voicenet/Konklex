package org.apollo.game.event.impl;

import org.apollo.net.release.r317.ThirdNpcOptionEventDecoder;

/**
 * An {@link NpcOptionEvent} for the {@link ThirdNpcOptionEventDecoder}
 * @author Steve
 */
public final class ThirdNpcOptionEvent extends NpcOptionEvent {

	/**
	 * Create a new third npc option event.
	 * @param slot The slot that was found.
	 */
	public ThirdNpcOptionEvent(int slot) {
		super(3, slot);
	}
}
