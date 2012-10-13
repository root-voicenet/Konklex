package org.apollo.net.release.r668;

import org.apollo.game.event.impl.PositionEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link PositionEvent}.
 * @author Steve
 */
public class PositionEventEncoder extends EventEncoder<PositionEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(PositionEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(46);
		final Position base = event.getBase(), pos = event.getPosition();
		builder.put(DataType.BYTE, DataTransformation.ADD, pos.getLocalY(base));
		builder.put(DataType.BYTE, pos.getLocalX(base));
		builder.put(DataType.BYTE, DataTransformation.ADD, pos.getHeight());
		return builder.toGamePacket();
	}
}