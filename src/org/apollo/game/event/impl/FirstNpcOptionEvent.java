package org.apollo.game.event.impl;

import org.apollo.net.release.r317.FirstNpcOptionEventDecoder;

/**
 * An {@link NpcOptionEvent} for the {@link FirstNpcOptionEventDecoder}
 * @author Steve
 */
public final class FirstNpcOptionEvent extends NpcOptionEvent {

    /**
     * Create a new first npc option event.
     * @param slot The slot that was found.
     */
    public FirstNpcOptionEvent(int slot) {
	super(1, slot);
    }
}
