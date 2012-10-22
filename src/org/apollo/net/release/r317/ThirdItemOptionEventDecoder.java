package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ThirdItemOptionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ThirdItemOptionEvent}.
 * @author Chris Fletcher
 */
public final class ThirdItemOptionEventDecoder extends EventDecoder<ThirdItemOptionEvent> {

	@Override
	public ThirdItemOptionEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		final int slot = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		final int interfaceId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		return new ThirdItemOptionEvent(interfaceId, id, slot);
	}
}
