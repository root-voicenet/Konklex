package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SetInterfacePlayerModelEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SetInterfacePlayerModelEvent}.
 * @author Chris Fletcher
 */
public final class SetInterfacePlayerModelEventEncoder extends EventEncoder<SetInterfacePlayerModelEvent> {

	@Override
	public GamePacket encode(SetInterfacePlayerModelEvent event) {
		final GamePacketBuilder builder = new GamePacketBuilder(185);
		builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, event.getInterfaceId());
		return builder.toGamePacket();
	}
}