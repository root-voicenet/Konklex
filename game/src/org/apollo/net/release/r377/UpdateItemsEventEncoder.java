package org.apollo.net.release.r377;

import org.apollo.game.event.impl.UpdateItemsEvent;
import org.apollo.game.model.Item;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link UpdateItemsEvent}.
 * @author Graham
 */
public final class UpdateItemsEventEncoder extends EventEncoder<UpdateItemsEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(UpdateItemsEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(206, PacketType.VARIABLE_SHORT);
		final Item[] items = event.getItems();
		builder.put(DataType.SHORT, event.getInterfaceId());
		builder.put(DataType.SHORT, items.length);
		for (final Item item : items) {
			final int id = item == null ? -1 : item.getId();
			final int amount = item == null ? 0 : item.getAmount();
			builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, id + 1);
			if (amount > 254) {
				builder.put(DataType.BYTE, DataTransformation.NEGATE, 255);
				builder.put(DataType.INT, DataOrder.LITTLE, amount);
			}
			else
				builder.put(DataType.BYTE, DataTransformation.NEGATE, amount);
		}
		return builder.toGamePacket();
	}
}
