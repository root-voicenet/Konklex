package org.apollo.net.release.r317;

import org.apollo.game.event.impl.CommandEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link CommandEvent}.
 * @author Graham
 */
public final class CommandEventDecoder extends EventDecoder<CommandEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.GamePacket)
	 */
	@Override
	public CommandEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		return new CommandEvent(reader.getString());
	}
}
