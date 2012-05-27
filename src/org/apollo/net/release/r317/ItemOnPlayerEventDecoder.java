package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ItemOnPlayerEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ItemOnPlayerEvent}
 * @author Steve
 */
public final class ItemOnPlayerEventDecoder extends EventDecoder<ItemOnPlayerEvent> {

    @Override
    public ItemOnPlayerEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int interfaceId = (short) reader.getSigned(DataType.SHORT, DataTransformation.ADD);
	final int playerIndex = (short) reader.getSigned(DataType.SHORT);
	final int itemId = (int) reader.getSigned(DataType.SHORT);
	final int itemSlot = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
	return new ItemOnPlayerEvent(interfaceId, playerIndex, itemId, itemSlot);
    }
}
