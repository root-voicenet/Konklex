package org.apollo.net.release.r668;

import org.apollo.game.event.impl.LogoutEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link LogoutEvent}.
 * @author Steve
 */
public final class LogoutEventEncoder extends EventEncoder<LogoutEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(LogoutEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(51);
		return builder.toGamePacket();
	}
}
