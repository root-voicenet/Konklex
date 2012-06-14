package org.apollo.net.release.r317;

import org.apollo.game.event.impl.PlayerReportEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link PlayerReportEvent}
 * @author Steve
 */
public final class PlayerReportEventDecoder extends EventDecoder<PlayerReportEvent> {

    @Override
    public PlayerReportEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final long player = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
	final int rule = (int) reader.getUnsigned(DataType.SHORT, DataOrder.BIG);
	return new PlayerReportEvent(player, rule);
    }
}
