package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SystemUpdateEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SystemUpdateEvent}
 * @author Steve
 */
public final class SystemUpdateEventEncoder extends EventEncoder<SystemUpdateEvent> {

	@Override
	public GamePacket encode(SystemUpdateEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(114);
		builder.put(DataType.SHORT, DataOrder.LITTLE, event.getTime());
		return builder.toGamePacket();
	}
}