package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SecondNpcOptionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link SecondNpcOptionEvent}.
 * 
 * @author Steve
 */
public final class SecondNpcOptionEventDecoder extends
EventDecoder<SecondNpcOptionEvent> {

	@Override
	public SecondNpcOptionEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int slot = (int) reader.getUnsigned(DataType.SHORT,
				DataOrder.LITTLE);
		return new SecondNpcOptionEvent(slot);
	}
}