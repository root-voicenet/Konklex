package org.apollo.net.release.r317;

import org.apollo.game.event.impl.DestroyGroundEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link DestroyGroundEvent}.
 * @author Steve
 */
public final class DestroyGroundEventEncoder extends EventEncoder<DestroyGroundEvent> {

    @Override
    public GamePacket encode(DestroyGroundEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(156);
	builder.put(DataType.BYTE, DataTransformation.SUBTRACT, 0);
	builder.put(DataType.SHORT, event.getGroundItem().getItem().getId());
	return builder.toGamePacket();
    }

}
