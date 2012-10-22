package org.apollo.net.release.r668;

import org.apollo.game.event.impl.PlayerIdleEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link PlayerIdleEvent}
 * @author Steve
 */
public final class PlayerIdleEventDecoder extends EventDecoder<PlayerIdleEvent> {

	@Override
	public PlayerIdleEvent decode(GamePacket packet) {
		return new PlayerIdleEvent();
	}
}
