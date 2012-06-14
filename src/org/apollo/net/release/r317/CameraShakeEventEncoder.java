package org.apollo.net.release.r317;

import org.apollo.game.event.impl.CameraShakeEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link CameraShakeEvent}
 * @author Steve
 */
public final class CameraShakeEventEncoder extends EventEncoder<CameraShakeEvent> {

    @Override
    public GamePacket encode(CameraShakeEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(35);
	builder.put(DataType.BYTE, event.getType());
	for (int i = 0; i < CameraShakeEvent.MAX_MAGNITUDES; i++)
	    builder.put(DataType.BYTE, event.getMagnitudes()[i]);
	return builder.toGamePacket();
    }
}
