package org.apollo.net.release.r317;

import org.apollo.game.event.impl.CameraResetEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link CameraResetEvent}
 * @author Steve
 */
public final class CameraResetEventEncoder extends EventEncoder<CameraResetEvent> {

	@Override
	public GamePacket encode(CameraResetEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(107);
		return builder.toGamePacket();
	}
}
