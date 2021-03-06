package org.apollo.net.release.r377;

import org.apollo.game.event.impl.OpenInterfaceSidebarEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link OpenInterfaceSidebarEvent}.
 * @author Graham
 */
public final class OpenInterfaceSidebarEventEncoder extends EventEncoder<OpenInterfaceSidebarEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(OpenInterfaceSidebarEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(128);
		builder.put(DataType.SHORT, DataTransformation.ADD, event.getInterfaceId());
		builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, event.getSidebarId());
		return builder.toGamePacket();
	}
}
