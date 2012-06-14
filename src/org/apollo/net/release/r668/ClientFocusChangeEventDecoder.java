package org.apollo.net.release.r668;

import org.apollo.game.event.impl.ClientFocusChangeEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ClientFocusChangeEvent}.
 * @author Steve
 */
public final class ClientFocusChangeEventDecoder extends EventDecoder<ClientFocusChangeEvent> {

    @Override
    public ClientFocusChangeEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final boolean focus = reader.getSigned(DataType.BYTE) == 1;
	return new ClientFocusChangeEvent(focus);
    }
}
