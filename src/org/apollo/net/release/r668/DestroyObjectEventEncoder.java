package org.apollo.net.release.r668;

import org.apollo.game.event.impl.DestroyObjectEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link FirstObjectEvent}.
 * @author Steve
 */
public class DestroyObjectEventEncoder extends EventEncoder<DestroyObjectEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
	 */
	@Override
	public GamePacket encode(DestroyObjectEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(45);
		builder.put(DataType.BYTE, DataTransformation.NEGATE, 0); //Useless
		builder.put(DataType.BYTE, (event.getObject() << 2) + (event.getOrient() & 0x3));
		return builder.toGamePacket();
	}
}
