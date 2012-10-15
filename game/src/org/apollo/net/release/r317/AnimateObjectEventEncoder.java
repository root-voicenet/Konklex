package org.apollo.net.release.r317;

import org.apollo.game.event.impl.AnimateObjectEvent;
import org.apollo.game.model.Animation;
import org.apollo.game.model.GameObject;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link AnimateObjectEvent}
 * @author Steve
 */
public final class AnimateObjectEventEncoder extends EventEncoder<AnimateObjectEvent> {

	@Override
	public GamePacket encode(AnimateObjectEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(160);
		final GameObject object = event.getObject();
		final Animation animation = event.getAnimation();
		builder.put(DataType.BYTE, DataTransformation.SUBTRACT, 0); // implement this accordingly
		builder.put(DataType.BYTE, DataTransformation.SUBTRACT, (object.getType() << 2) + (object.getRotation() & 3));
		builder.put(DataType.SHORT, DataTransformation.ADD, animation.getId());
		return builder.toGamePacket();
	}

}
