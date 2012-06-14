package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FirstItemOptionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FirstItemOptionEvent}.
 * @author Chris Fletcher
 */
public final class FirstItemOptionEventDecoder extends EventDecoder<FirstItemOptionEvent> {

    @Override
    public FirstItemOptionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int interfaceId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	final int slot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	final int id = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
	return new FirstItemOptionEvent(interfaceId, id, slot);
    }
}
