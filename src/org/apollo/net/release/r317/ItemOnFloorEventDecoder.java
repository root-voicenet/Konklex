package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ItemOnFloorEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ItemOnFloorEvent}
 * @author Steve
 */
public final class ItemOnFloorEventDecoder extends EventDecoder<ItemOnFloorEvent> {

	@Override
	public ItemOnFloorEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int interfaceId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int floorId = (int) reader.getSigned(DataType.SHORT);
		int y = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		int slot = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		int x = (int) reader.getSigned(DataType.SHORT);
		return new ItemOnFloorEvent(interfaceId, id, floorId, slot, new Position(x, y));
	}
}
