package org.apollo.net.release.r474;

import org.apollo.game.event.impl.ButtonEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ButtonEvent}.
 * @author Steve
 */
public final class ButtonEventDecoder extends EventDecoder<ButtonEvent> {

	@Override
	public ButtonEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int interfaceId = reader.getBit();
		return new ButtonEvent(interfaceId);
	}
}
