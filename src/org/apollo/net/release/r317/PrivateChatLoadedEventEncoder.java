package org.apollo.net.release.r317;

import org.apollo.game.event.impl.PrivateChatLoadedEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link PrivateChatLoadedEvent}.
 * @author Steve
 */
public final class PrivateChatLoadedEventEncoder extends EventEncoder<PrivateChatLoadedEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
     */
    @Override
    public GamePacket encode(PrivateChatLoadedEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(221);
	builder.put(DataType.BYTE, event.getId());
	return builder.toGamePacket();
    }
}
