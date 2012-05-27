package org.apollo.net.release.r317;

import org.apollo.game.event.impl.PlayerFollowEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link PlayerFollowEvent}
 * @author Steve
 */
public final class PlayerFollowEventDecoder extends EventDecoder<PlayerFollowEvent> {

	@Override
	public PlayerFollowEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int playerIndex = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		return new PlayerFollowEvent(playerIndex);
	}
}