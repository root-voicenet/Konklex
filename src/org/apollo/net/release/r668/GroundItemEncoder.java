package org.apollo.net.release.r668;

import org.apollo.game.event.impl.GroundItemEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link GroundItemEventEncoder}.
 * @author Steve
 */
public final class GroundItemEncoder extends EventEncoder<GroundItemEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(GroundItemEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(48);
		builder.put(DataType.BYTE, DataTransformation.NEGATE, 0);
		builder.put(DataType.SHORT, DataTransformation.ADD, event.getId());
		builder.put(DataType.SHORT, event.getItemAmount());
		return builder.toGamePacket();
	}
}