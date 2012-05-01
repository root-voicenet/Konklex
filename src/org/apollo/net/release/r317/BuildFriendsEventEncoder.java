package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FriendEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link FriendEvent}.
 * @author Steve
 */
public class BuildFriendsEventEncoder extends EventEncoder<FriendEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(FriendEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(50);
		builder.putLong(event.getFriendLong());
		builder.put(DataType.BYTE, event.getWorld() != 0 ? event.getWorld() + 9 : event.getWorld() + 1);
		return builder.toGamePacket();
	}
}
