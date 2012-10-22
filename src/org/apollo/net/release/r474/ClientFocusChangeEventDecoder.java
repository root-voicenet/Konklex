package org.apollo.net.release.r474;

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
		GamePacketReader reader = new GamePacketReader(packet);
		boolean active = reader.getUnsigned(DataType.BYTE) == 1;
		return new ClientFocusChangeEvent(active);
	}

}
