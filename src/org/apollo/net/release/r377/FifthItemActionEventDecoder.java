package org.apollo.net.release.r377;

import org.apollo.game.event.impl.FifthItemActionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FifthItemActionEvent}.
 * 
 * @author Graham
 */
public final class FifthItemActionEventDecoder extends
EventDecoder<FifthItemActionEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
	 * GamePacket)
	 */
	@Override
	public FifthItemActionEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int slot = (int) reader.getUnsigned(DataType.SHORT,
				DataOrder.LITTLE, DataTransformation.ADD);
		final int id = (int) reader.getUnsigned(DataType.SHORT,
				DataOrder.LITTLE, DataTransformation.ADD);
		final int interfaceId = (int) reader.getUnsigned(DataType.SHORT,
				DataOrder.LITTLE);
		return new FifthItemActionEvent(interfaceId, id, slot);
	}
}
