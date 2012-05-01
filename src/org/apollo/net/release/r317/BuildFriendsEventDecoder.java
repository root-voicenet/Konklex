package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FriendEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;
import org.apollo.net.release.EventEncoder;
import org.apollo.util.NameUtil;
import org.apollo.util.TextUtil;

/**
 * An {@link EventEncoder} for the {@link FriendEvent}
 * @author Steve
 */
public class BuildFriendsEventDecoder extends EventDecoder<FriendEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.GamePacket)
	 */
	@Override
	public FriendEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		long l = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
		String friend = TextUtil.capitalize(NameUtil.decodeBase37(l));
		return new FriendEvent(friend, packet.getOpcode());
	}
}