package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FifthItemActionEvent;
import org.apollo.game.event.impl.FourthItemActionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FourthItemActionEvent}.
 * @author Graham
 */
public final class FifthItemActionEventDecoder extends EventDecoder<FifthItemActionEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public FifthItemActionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int slot = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	final int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	final int id = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	return new FifthItemActionEvent(interfaceId, id, slot);
    }
}
