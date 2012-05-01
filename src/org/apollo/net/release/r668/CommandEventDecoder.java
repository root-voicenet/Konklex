package org.apollo.net.release.r668;

import org.apollo.game.event.impl.CommandEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link CommandEvent}.
 * @author Steve
 */
public final class CommandEventDecoder extends EventDecoder<CommandEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.GamePacket)
	 */
	@SuppressWarnings("unused")
	@Override
	public CommandEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		boolean clientCommand = reader.getUnsigned(DataType.BYTE) == 1;
		boolean unknown = reader.getUnsigned(DataType.BYTE) == 1;
		return new CommandEvent(reader.getString());
	}
}
