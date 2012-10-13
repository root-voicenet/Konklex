package org.apollo.net.release.r377;

import org.apollo.game.event.impl.SwitchTabInterfaceEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SwitchTabInterfaceEvent}.
 * @author Graham
 */
public final class SwitchTabInterfaceEventEncoder extends EventEncoder<SwitchTabInterfaceEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(SwitchTabInterfaceEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(10);
		builder.put(DataType.BYTE, DataTransformation.SUBTRACT, event.getTabId());
		builder.put(DataType.SHORT, DataTransformation.ADD, event.getInterfaceId());
		return builder.toGamePacket();
	}
}
