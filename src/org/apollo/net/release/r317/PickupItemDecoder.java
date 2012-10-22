package org.apollo.net.release.r317;

import org.apollo.game.event.impl.PickupItemEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link GroundItemEventEncoder}.
 * @author Arrowzftw
 */
public final class PickupItemDecoder extends EventDecoder<PickupItemEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game. GamePacket)
	 */
	@Override
	public PickupItemEvent decode(GamePacket packet) {
		final GamePacketReader reader = new GamePacketReader(packet);
		final int y = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		final int id = (int) reader.getSigned(DataType.SHORT);
		final int x = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
		return new PickupItemEvent(id, x, y);
	}
}