package org.apollo.net.release.r317;

import org.apollo.game.event.impl.DestroyObjectEvent;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link DestroyObjectEvent}.
 * @author Steve
 */
public final class DestroyObjectEventEncoder extends EventEncoder<DestroyObjectEvent> {

	@Override
	public GamePacket encode(DestroyObjectEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(101);
		final GameObject object = event.getObject();
		final Position position = event.getPosition();
		final int offset = position.getLocalSectorX() << 4 | position.getLocalSectorY();
		builder.put(DataType.BYTE, DataTransformation.NEGATE, (object.getType() << 2) + (object.getRotation() & 3));
		builder.put(DataType.BYTE, offset);
		return builder.toGamePacket();
	}

}
