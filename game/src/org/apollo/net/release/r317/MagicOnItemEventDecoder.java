package org.apollo.net.release.r317;

import org.apollo.game.event.impl.MagicOnItemEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link MagicOnItemEvent}.
 * 
 * @author Chris Fletcher
 */
public final class MagicOnItemEventDecoder extends EventDecoder<MagicOnItemEvent> {

	@Override
	public MagicOnItemEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int slot = (int) reader.getUnsigned(DataType.SHORT);
		final int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		final int interfaceId = (int) reader.getUnsigned(DataType.SHORT);
		final int spell = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		return new MagicOnItemEvent(interfaceId, id, slot, spell);
	}
}