package org.apollo.net.release.r508;

import org.apollo.game.event.impl.CommandEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link CommandEvent}
 * @author Steve
 */
public final class CommandEventDecoder extends EventDecoder<CommandEvent> {

	@Override
	public CommandEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		return new CommandEvent(reader.getString());
	}

}
