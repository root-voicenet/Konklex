package org.apollo.net.release.r317;

import org.apollo.game.event.impl.EnterAmountEvent;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * An {@link EventEncoder} for the {@link EnterAmountEvent}.
 * @author Graham
 */
public final class EnterAmountEventEncoder extends EventEncoder<EnterAmountEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(EnterAmountEvent event) {
		return new GamePacket(27, PacketType.FIXED, ChannelBuffers.EMPTY_BUFFER);
	}
}
