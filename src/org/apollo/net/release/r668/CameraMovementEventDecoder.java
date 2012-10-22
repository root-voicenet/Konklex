package org.apollo.net.release.r668;

import org.apollo.game.event.impl.CameraMovementEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link CameraMovementEventDecoder}.
 * @author Steve
 */
public final class CameraMovementEventDecoder extends EventDecoder<CameraMovementEvent> {

	@Override
	public CameraMovementEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int y = (int) reader.getUnsigned(DataType.SHORT);
		final int x = (int) reader.getUnsigned(DataType.SHORT);
		return new CameraMovementEvent(x, y);
	}
}
