package org.apollo.net.release.r317;

import org.apollo.game.event.impl.MagicOnObjectEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link MagicOnObjectEvent}
 * @author Steve
 */
public final class MagicOnObjectEventDecoder extends EventDecoder<MagicOnObjectEvent> {

	@Override
	public MagicOnObjectEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int x = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		int magicId = (int) reader.getSigned(DataType.SHORT, DataOrder.BIG, DataTransformation.ADD);
		int y = (int) reader.getSigned(DataType.SHORT, DataOrder.BIG, DataTransformation.ADD);
		int objectId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		return new MagicOnObjectEvent(x, y, magicId, objectId);
	}

}
