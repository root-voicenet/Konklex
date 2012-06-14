package org.apollo.net.release.r317;

import org.apollo.game.event.impl.WalkEvent;
import org.apollo.game.model.Position;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.EventDecoder;

/**
 * An {@link EventDecoder} for the {@link WalkEvent}.
 * @author Graham
 */
public final class WalkEventDecoder extends EventDecoder<WalkEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventDecoder#decode(org.apollo.net.codec.game.
     * GamePacket)
     */
    @Override
    public WalkEvent decode(GamePacket packet) {
	final GamePacketReader reader = new GamePacketReader(packet);
	int length = packet.getLength();
	if (packet.getOpcode() == 248)
	    length -= 14; // strip off anti-cheat data
	final int steps = (length - 5) / 2;
	final int[][] path = new int[steps][2];
	final int x = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD);
	for (int i = 0; i < steps; i++) {
	    path[i][0] = (int) reader.getSigned(DataType.BYTE);
	    path[i][1] = (int) reader.getSigned(DataType.BYTE);
	}
	final int y = (int) reader.getUnsigned(DataType.SHORT, DataOrder.LITTLE);
	final boolean run = reader.getUnsigned(DataType.BYTE, DataTransformation.NEGATE) == 1;
	final Position[] positions = new Position[steps + 1];
	positions[0] = new Position(x, y);
	for (int i = 0; i < steps; i++)
	    positions[i + 1] = new Position(path[i][0] + x, path[i][1] + y);
	return new WalkEvent(positions, run);
    }
}
