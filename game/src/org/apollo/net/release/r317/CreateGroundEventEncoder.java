package org.apollo.net.release.r317;

import org.apollo.game.event.impl.CreateGroundEvent;
import org.apollo.game.model.GroundItem;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link CreateGroundEvent}.
 * @author Steve
 */
public final class CreateGroundEventEncoder extends EventEncoder<CreateGroundEvent> {

	@Override
	public GamePacket encode(CreateGroundEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(44);
		final GroundItem item = event.getGroundItem();
		builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, item.getItem().getId());
		builder.put(DataType.SHORT, item.getItem().getAmount());
		builder.put(DataType.BYTE, 0);
		return builder.toGamePacket();
	}

}
