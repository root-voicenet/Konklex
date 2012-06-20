package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FirstPlayerOptionEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FirstPlayerOptionEvent}.
 * @author Steve
 */
public final class FirstPlayerOptionEventDecoder extends EventDecoder<FirstPlayerOptionEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public FirstPlayerOptionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int otherId = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	return new FirstPlayerOptionEvent(otherId);
    }
}
