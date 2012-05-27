package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ItemUsedOnObjectEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ItemUsedOnObjectEvent}.
 * @author Steve
 */
public class ItemUsedOnObjectDecoder extends EventDecoder<ItemUsedOnObjectEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.GamePacket)
	 */
	@Override
	public ItemUsedOnObjectEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		int object = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
		int y = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		int slot = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD) - 128;
		int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		int id = (int) reader.getUnsigned(DataType.SHORT);
		return new ItemUsedOnObjectEvent(object, slot, id, x, y);
	}
}
