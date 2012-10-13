package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FriendsListEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link FriendsListEvent}
 * @author Steve
 */
public final class FriendsListEventDecoder extends EventDecoder<FriendsListEvent> {

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
	 * GamePacket)
	 */
	@Override
	public FriendsListEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final long friend = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
		return new FriendsListEvent(packet.getOpcode(), friend);
	}
}