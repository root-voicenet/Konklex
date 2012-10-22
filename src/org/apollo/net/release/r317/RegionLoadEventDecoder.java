package org.apollo.net.release.r317;

import org.apollo.game.event.impl.RegionLoadEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link RegionLoadEvent}.
 * @author Steve
 */
public final class RegionLoadEventDecoder extends EventDecoder<RegionLoadEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game. GamePacket)
	 */
	@Override
	public RegionLoadEvent decode(GamePacket packet) {
		return new RegionLoadEvent();
	}
}
