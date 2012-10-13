package org.apollo.net.release.r317;

import org.apollo.game.event.impl.CloseInterfaceEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link CloseInterfaceEvent}.
 * @author Graham
 */
public final class CloseInterfaceEventEncoder extends EventEncoder<CloseInterfaceEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(CloseInterfaceEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(219);
		return builder.toGamePacket();
	}
}
