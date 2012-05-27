package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SecondPlayerOptionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link SecondPlayerOptionEvent}.
 * @author Steve
 */
public final class SecondPlayerOptionEventDecoder extends EventDecoder<SecondPlayerOptionEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.GamePacket)
	 */
	@Override
	public SecondPlayerOptionEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		final int otherId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		return new SecondPlayerOptionEvent(otherId);
	}
}
