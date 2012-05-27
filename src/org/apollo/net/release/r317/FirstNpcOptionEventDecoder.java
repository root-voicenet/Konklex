package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FirstNpcOptionEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FirstNpcOptionEvent}
 * @author Steve
 */
public final class FirstNpcOptionEventDecoder extends EventDecoder<FirstNpcOptionEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public FirstNpcOptionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int slot = (int) reader.getSigned(DataType.SHORT, DataTransformation.ADD);
	return new FirstNpcOptionEvent(slot);
    }
}