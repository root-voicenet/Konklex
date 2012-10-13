package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SendFriendEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SendFriendEvent}.
 * @author Steve
 */
public final class SendFriendEventEncoder extends EventEncoder<SendFriendEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(SendFriendEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(50);
		builder.putLong(event.getFriend());
		builder.put(DataType.BYTE, event.getStatus());
		return builder.toGamePacket();
	}
}
