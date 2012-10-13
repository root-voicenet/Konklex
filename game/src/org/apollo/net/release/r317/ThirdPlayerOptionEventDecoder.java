package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ThirdPlayerOptionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ThirdPlayerOptionEvent}.
 * @author Steve
 */
public final class ThirdPlayerOptionEventDecoder extends EventDecoder<ThirdPlayerOptionEvent> {

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
	 * GamePacket)
	 */
	@Override
	public ThirdPlayerOptionEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int otherId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		return new ThirdPlayerOptionEvent(otherId);
	}
}
