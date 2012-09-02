package org.apollo.net.release.r317;

import org.apollo.game.event.impl.DebugMessageEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link DebugMessageEvent}.
 * @author Steve
 */
public final class DebugMessageEventEncoder extends EventEncoder<DebugMessageEvent> {

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(DebugMessageEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(195, PacketType.VARIABLE_BYTE);
		builder.putString(event.getMessage());
		return builder.toGamePacket();
	}
}
