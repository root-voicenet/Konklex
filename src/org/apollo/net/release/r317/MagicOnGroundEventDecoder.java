package org.apollo.net.release.r317;

import org.apollo.game.event.impl.MagicOnGroundEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link MagicOnGroundEvent}
 * @author Steve
 */
public final class MagicOnGroundEventDecoder extends EventDecoder<MagicOnGroundEvent> {

	@Override
	public MagicOnGroundEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int y = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		int itemId = (int) reader.getSigned(DataType.SHORT);
		int x = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		int magicId = (int) reader.getSigned(DataType.SHORT, DataTransformation.ADD);
		return new MagicOnGroundEvent(x, y, magicId, itemId);
	}

}
