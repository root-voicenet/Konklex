package org.apollo.net.release.r377;

import org.apollo.game.event.impl.FirstItemActionEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link FirstItemActionEvent}.
 * @author Graham
 */
public final class FirstItemActionEventDecoder extends EventDecoder<FirstItemActionEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public FirstItemActionEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	final int id = (int) reader.getUnsigned(DataType.SHORT, DataTransformation.ADD);
	final int interfaceId = (int) reader.getUnsigned(DataType.SHORT);
	final int slot = (int) reader.getUnsigned(DataType.SHORT);
	return new FirstItemActionEvent(interfaceId, id, slot);
    }
}
