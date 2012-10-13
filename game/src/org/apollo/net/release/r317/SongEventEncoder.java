package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SongEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SongEvent}.
 * @author Steve
 */
public final class SongEventEncoder extends EventEncoder<SongEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(SongEvent event) {
		GamePacketBuilder builder = null;
		switch (event.getType()) {
		case 1:
			builder = new GamePacketBuilder(74);
			builder.put(DataType.SHORT, DataOrder.LITTLE, event.getSound());
			break;
		case 2:
			builder = new GamePacketBuilder(121);
			builder.put(DataType.SHORT, DataOrder.LITTLE, event.getSound());
			builder.put(DataType.SHORT, DataOrder.LITTLE, event.getDelay());
			break;
		case 3:
			builder = new GamePacketBuilder(174);
			builder.put(DataType.SHORT, DataOrder.LITTLE, event.getSound());
			builder.put(DataType.SHORT, DataOrder.LITTLE, event.getSoundType());
			builder.put(DataType.SHORT, DataOrder.LITTLE, event.getDelay());
			break;
		}
		return builder.toGamePacket();
	}
}
