package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ItemOnItemEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ItemOnItemEventDecoder}.
 * @author Chris Fletcher
 */
public final class ItemOnItemDecoder extends EventDecoder<ItemOnItemEvent> {

	@Override
	public ItemOnItemEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int targetSlot = (int) reader.getUnsigned(DataType.SHORT);
		final int usedSlot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		final int targetId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		final int targetInterface = (int) reader.getUnsigned(DataType.SHORT);
		final int usedId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		final int usedInterface = (int) reader.getUnsigned(DataType.SHORT);
		return new ItemOnItemEvent(usedInterface, usedId, usedSlot, targetInterface, targetId, targetSlot);
	}
}