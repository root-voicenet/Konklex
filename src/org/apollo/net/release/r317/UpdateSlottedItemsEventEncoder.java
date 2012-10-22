package org.apollo.net.release.r317;

import org.apollo.game.event.impl.UpdateSlottedItemsEvent;
import org.apollo.game.model.Item;
import org.apollo.game.model.SlottedItem;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link UpdateSlottedItemsEvent}.
 * @author Graham
 */
public final class UpdateSlottedItemsEventEncoder extends EventEncoder<UpdateSlottedItemsEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(UpdateSlottedItemsEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(34, PacketType.VARIABLE_SHORT);
		final SlottedItem[] items = event.getSlottedItems();
		builder.put(DataType.SHORT, event.getInterfaceId());
		for (final SlottedItem slottedItem : items) {
			builder.putSmart(slottedItem.getSlot());
			final Item item = slottedItem.getItem();
			final int id = item == null ? -1 : item.getId();
			final int amount = item == null ? 0 : item.getAmount();
			builder.put(DataType.SHORT, id + 1);
			if (amount > 254) {
				builder.put(DataType.BYTE, 255);
				builder.put(DataType.INT, amount);
			}
			else
				builder.put(DataType.BYTE, amount);
		}
		return builder.toGamePacket();
	}
}
