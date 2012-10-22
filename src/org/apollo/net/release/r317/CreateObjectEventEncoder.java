package org.apollo.net.release.r317;

import org.apollo.game.event.impl.CreateObjectEvent;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.Position;
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
		final GamePacketBuilder builder = new GamePacketBuilder(151);
		final GameObject object = event.getObject();
		final Position position = event.getPosition();
		final int offset = position.getLocalSectorX() << 4 | position.getLocalSectorY();
		builder.put(DataType.BYTE, DataTransformation.ADD, offset);
		builder.put(DataType.SHORT, DataOrder.LITTLE, object.getDefinition().getId());
		builder.put(DataType.BYTE, DataTransformation.SUBTRACT, (object.getType() << 2) + (object.getRotation() & 3));
		return builder.toGamePacket();
	}

}
