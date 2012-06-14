package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ThirdItemActionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link ThirdItemActionEvent}.
 * @author Graham
 */
public final class ThirdItemActionEventDecoder extends EventDecoder<ThirdItemActionEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public ThirdItemActionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int interfaceId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	final int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	final int slot = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	return new ThirdItemActionEvent(interfaceId, id, slot);
    }
}
