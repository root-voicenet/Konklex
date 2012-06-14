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
	final GamePacketReader reader = new GamePacketReader(packet);
	final int interfaceId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
	final int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	final int floorId = (int) reader.getSigned(DataType.SHORT);
	final int y = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	final int slot = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	final int x = (int) reader.getSigned(DataType.SHORT);
	return new ItemOnFloorEvent(interfaceId, id, floorId, slot, new Position(x, y));
    }
}
