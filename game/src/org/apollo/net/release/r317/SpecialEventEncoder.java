package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SpecialEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SpecialEvent}
 * @author Steve
 */
public final class SpecialEventEncoder extends EventEncoder<SpecialEvent> {

	@Override
	public GamePacket encode(SpecialEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(70);
		builder.put(DataType.SHORT, event.getAmount());
		builder.put(DataType.SHORT, DataOrder.LITTLE, 0);
		builder.put(DataType.SHORT, DataOrder.LITTLE, event.getBar());
		return builder.toGamePacket();
	}

}
