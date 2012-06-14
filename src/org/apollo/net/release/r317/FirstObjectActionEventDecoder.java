package org.apollo.net.release.r317;

import org.apollo.game.event.impl.FirstObjectActionEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FirstObjectActionEvent}.
 * @author Graham
 */
public final class FirstObjectActionEventDecoder extends EventDecoder<FirstObjectActionEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public FirstObjectActionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	final int id = (int) reader.getUnsigned(DataType.SHORT);
	final int y = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	return new FirstObjectActionEvent(id, new Position(x, y));
    }
}
