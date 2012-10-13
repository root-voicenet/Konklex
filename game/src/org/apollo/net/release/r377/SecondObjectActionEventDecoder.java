package org.apollo.net.release.r377;

import org.apollo.game.event.impl.SecondObjectActionEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link SecondObjectActionEvent}.
 * 
 * @author Graham
 */
public final class SecondObjectActionEventDecoder extends
EventDecoder<SecondObjectActionEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
	 * GamePacket)
	 */
	@Override
	public SecondObjectActionEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int id = (int) reader.getUnsigned(DataType.SHORT);
		final int x = (int) reader.getUnsigned(DataType.SHORT);
		final int y = (int) reader.getUnsigned(DataType.SHORT,
				DataTransformation.ADD);
		return new SecondObjectActionEvent(id, new Position(x, y));
	}
}
