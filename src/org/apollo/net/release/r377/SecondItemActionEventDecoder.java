package org.apollo.net.release.r377;

import org.apollo.game.event.impl.SecondItemActionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link SecondItemActionEvent}.
 * @author Graham
 */
public final class SecondItemActionEventDecoder extends EventDecoder<SecondItemActionEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game. GamePacket)
	 */
	@Override
	public SecondItemActionEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int slot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		final int id = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		final int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		return new SecondItemActionEvent(interfaceId, id, slot);
	}
}
