package org.apollo.net.release.r317;

import org.apollo.game.event.impl.CreateObjectEvent;
import org.apollo.game.model.GameObject;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link CreateObjectEvent}.
 * @author Steve
 */
public final class CreateObjectEventEncoder extends EventEncoder<CreateObjectEvent> {

	@Override
	public GamePacket encode(CreateObjectEvent event) {
		final GameObject object = event.getObject();
		final GamePacketBuilder builder = new GamePacketBuilder(151);
		builder.put(DataType.BYTE, DataTransformation.ADD, 0);
		builder.put(DataType.SHORT, DataOrder.LITTLE, object.getDefinition().getId());
		builder.put(DataType.BYTE, DataTransformation.SUBTRACT, (object.getType() << 2) + (object.getRotation() & 3));
		return builder.toGamePacket();
	}

}
