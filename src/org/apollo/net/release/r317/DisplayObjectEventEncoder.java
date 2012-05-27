package org.apollo.net.release.r317;

import org.apollo.game.event.impl.DisplayObjectEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SecondObjectEvent}.
 * @author Steve
 */
public class DisplayObjectEventEncoder extends EventEncoder<DisplayObjectEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(DisplayObjectEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(151);
		builder.put(DataType.BYTE, DataTransformation.ADD, 0);
		builder.put(DataType.SHORT, DataOrder.LITTLE, event.getObject());
		builder.put(DataType.BYTE, DataTransformation.SUBTRACT, (event.getTile() << 2) + (event.getOrient() & 3));
		return builder.toGamePacket();
	}
}