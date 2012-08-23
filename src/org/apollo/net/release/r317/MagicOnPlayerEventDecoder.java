package org.apollo.net.release.r317;

import org.apollo.game.event.impl.MagicOnPlayerEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link MagicOnPlayerEvent}
 * @author Steve
 */
public final class MagicOnPlayerEventDecoder extends EventDecoder<MagicOnPlayerEvent> {

	@Override
	public MagicOnPlayerEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int playerIndex = (short) reader.getSigned(DataType.SHORT, DataTransformation.ADD);
		final int spellId = (short) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		return new MagicOnPlayerEvent(playerIndex, spellId);
	}
}
