package org.apollo.net.release.r317;

import org.apollo.game.event.impl.BuildPlayerMenuEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link BuildPlayerMenuEvent}.
 * @author Steve
 */
public final class BuildPlayerMenuEventEncoder extends EventEncoder<BuildPlayerMenuEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
     */
    @Override
    public GamePacket encode(BuildPlayerMenuEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(104, PacketType.VARIABLE_BYTE);
	builder.put(DataType.BYTE, DataTransformation.NEGATE, event.getId());
	builder.put(DataType.BYTE, DataTransformation.ADD, event.isFirst() ? 1 : 0);
	builder.putString(event.getMessage());
	return builder.toGamePacket();
    }
}