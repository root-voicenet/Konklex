package org.apollo.game.event.impl;

import org.apollo.net.release.r317.SecondNpcOptionEventDecoder;

/**
 * An {@link NpcOptionEvent} for the {@link SecondNpcOptionEventDecoder}
 * @author Steve
 */
public final class SecondNpcOptionEvent extends NpcOptionEvent {

	/**
	 * Create a new second npc option event.
	 * @param slot The slot that was found.
	 */
	public SecondNpcOptionEvent(int slot) {
		super(2, slot);
	}
}
