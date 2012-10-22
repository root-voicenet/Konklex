package org.apollo.net.release.r317;

import org.apollo.game.event.impl.MagicOnNpcEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link MagicOnNpcEvent}
 * @author Steve
 */
public final class MagicOnNpcEventDecoder extends EventDecoder<MagicOnNpcEvent> {

	@Override
	public MagicOnNpcEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int index = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
		int spellId = (int) reader.getSigned(DataType.SHORT, DataTransformation.ADD);
		return new MagicOnNpcEvent(index, spellId);
	}
}
