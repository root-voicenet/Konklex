package org.apollo.net.release.r317;

import org.apollo.game.event.impl.RemoveGroundItemEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link GroundItemEventEncoder}.
 * @author Arrowzftw
 */
public final class RemoveGroundItemEncoder extends EventEncoder<RemoveGroundItemEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
     */
    @Override
    public GamePacket encode(RemoveGroundItemEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(156);
	builder.put(DataType.BYTE, DataTransformation.SUBTRACT, 0);
	builder.put(DataType.SHORT, event.getId());
	return builder.toGamePacket();
    }
}