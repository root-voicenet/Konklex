package org.apollo.net.release.r317;

import org.apollo.game.event.impl.OpenInterfaceOverlayEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link OpenInterfaceOverlayEvent}.
 * @author Steve
 */
public class OpenInterfaceOverlayEventEncoder extends EventEncoder<OpenInterfaceOverlayEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(OpenInterfaceOverlayEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(208);
		builder.put(DataType.SHORT, DataOrder.LITTLE, event.getId());
		return builder.toGamePacket();
	}
}
