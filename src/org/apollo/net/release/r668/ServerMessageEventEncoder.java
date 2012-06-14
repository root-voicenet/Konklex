package org.apollo.net.release.r668;

import org.apollo.game.event.impl.ServerMessageEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link ServerMessageEvent}
 * @author Steve
 */
public class ServerMessageEventEncoder extends EventEncoder<ServerMessageEvent> {

    @Override
    public GamePacket encode(ServerMessageEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(102, PacketType.VARIABLE_BYTE);
	builder.putSmart(101);
	builder.put(DataType.INT, 0);
	builder.put(DataType.BYTE, 0x1);
	builder.putString(event.getMessage());
	return builder.toGamePacket();
    }
}
