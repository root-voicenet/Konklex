package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ResetClientEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link ResetClientEvent}.
 * @author Steve
 */
public final class ResetClientEventEncoder extends EventEncoder<ResetClientEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(ResetClientEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(107);
		return builder.toGamePacket();
	}
}
