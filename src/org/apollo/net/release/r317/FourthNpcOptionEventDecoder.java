package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FourthNpcOptionEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FourthNpcOptionEvent}
 * @author Steve
 */
public final class FourthNpcOptionEventDecoder extends EventDecoder<FourthNpcOptionEvent> {

    @Override
    public FourthNpcOptionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int slot = (int) reader.getUnsigned(DataType.SHORT);
	return new FourthNpcOptionEvent(slot);
    }

}
