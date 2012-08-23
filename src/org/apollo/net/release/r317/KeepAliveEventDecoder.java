package org.apollo.net.release.r317;

import org.apollo.game.event.impl.KeepAliveEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.release.EventDecoder;

/**
 * A {@link EventDecoder} for the {@link KeepAliveEvent}.
 * @author Graham
 */
public final class KeepAliveEventDecoder extends EventDecoder<KeepAliveEvent> {

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
	 * GamePacket)
	 */
	@Override
	public KeepAliveEvent decode(GamePacket packet) {
		return new KeepAliveEvent();
	}
}
