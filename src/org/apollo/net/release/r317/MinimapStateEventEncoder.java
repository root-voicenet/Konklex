package org.apollo.net.release.r317;

import org.apollo.game.event.impl.MinimapStateEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link MinimapStateEvent}
 * @author Steve
 */
public final class MinimapStateEventEncoder extends EventEncoder<MinimapStateEvent> {

	@Override
	public GamePacket encode(MinimapStateEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(99);
		builder.put(DataType.BYTE, event.getState());
		return builder.toGamePacket();
	}
}
