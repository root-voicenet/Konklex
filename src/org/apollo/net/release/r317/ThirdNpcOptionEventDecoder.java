package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ThirdNpcOptionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ThirdNpcOptionEvent}.
 * 
 * @author Steve
 */
public final class ThirdNpcOptionEventDecoder extends
EventDecoder<ThirdNpcOptionEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
	 * GamePacket)
	 */
	@Override
	public ThirdNpcOptionEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int slot = (int) reader.getUnsigned(DataType.SHORT,
				DataOrder.LITTLE, DataTransformation.ADD);
		return new ThirdNpcOptionEvent(slot);
	}
}
