package org.apollo.net.release.r668;

import org.apollo.game.event.impl.ClosedInterfaceEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ClosedInterfaceEvent}.
 * @author Steve
 */
public final class ClosedInterfaceEventDecoder extends EventDecoder<ClosedInterfaceEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.GamePacket)
	 */
	@Override
	public ClosedInterfaceEvent decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int closedInterface = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
		return new ClosedInterfaceEvent(closedInterface);
	}
}
