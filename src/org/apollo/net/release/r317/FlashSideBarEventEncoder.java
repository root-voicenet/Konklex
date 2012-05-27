package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FlashSideBarEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link FlashSideBarEvent}.
 * @author Solid Snake
 */
public class FlashSideBarEventEncoder extends EventEncoder<FlashSideBarEvent> {

	public GamePacket encode(FlashSideBarEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(24);
		builder.put(DataType.BYTE, DataTransformation.ADD, event.getSideBar());
		return builder.toGamePacket();
	}
}