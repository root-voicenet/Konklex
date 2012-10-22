package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ResetButtonsEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link ResetButtonsEvent}
 * @author Steve
 */
public final class ResetButtonsEventEncoder extends EventEncoder<ResetButtonsEvent> {

	@Override
	public GamePacket encode(ResetButtonsEvent event) {
		return new GamePacketBuilder(68).toGamePacket();
	}
}
