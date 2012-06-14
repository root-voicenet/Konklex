package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FourthItemOptionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FourthItemOptionEvent}.
 * @author Chris Fletcher
 */
public final class FourthItemOptionEventDecoder extends EventDecoder<FourthItemOptionEvent> {

    @Override
    public FourthItemOptionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int interfaceId = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	final int slot = (int) reader.getSigned(DataType.SHORT, DataOrder.LITTLE);
	final int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	return new FourthItemOptionEvent(interfaceId, id, slot);
    }
}
