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
public final class ItemUsedOnObjectDecoder extends EventDecoder<ItemUsedOnObjectEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public ItemUsedOnObjectEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	final int object = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	final int y = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	final int slot = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD) - 128;
	final int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	final int id = (int) reader.getUnsigned(DataType.SHORT);
	return new ItemUsedOnObjectEvent(object, slot, id, x, y);
    }
}
