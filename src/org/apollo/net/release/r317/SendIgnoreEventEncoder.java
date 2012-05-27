package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SendIgnoreEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SendIgnoreEvent}
 * @author Steve
 */
public final class SendIgnoreEventEncoder extends EventEncoder<SendIgnoreEvent> {

	@Override
	public GamePacket encode(SendIgnoreEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(214, PacketType.VARIABLE_SHORT);
		for (Long ignore : event.getFriends()) {
			builder.putLong(ignore);
		}
		return builder.toGamePacket();
	}

}
