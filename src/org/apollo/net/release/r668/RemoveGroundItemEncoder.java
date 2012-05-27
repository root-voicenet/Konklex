package org.apollo.net.release.r668;

import org.apollo.game.event.impl.RemoveGroundItemEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link GroundItemEventEncoder}.
 * @author Steve
 */
public final class RemoveGroundItemEncoder extends EventEncoder<RemoveGroundItemEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
     */
    @Override
    public GamePacket encode(RemoveGroundItemEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(16);
	builder.put(DataType.SHORT, event.getId());
	builder.put(DataType.BYTE, 0);
	return builder.toGamePacket();
    }
}