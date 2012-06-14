package org.apollo.net.release.r317;

import org.apollo.game.event.impl.HintIconEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link HintIconEvent}.
 * @author Solid Snake
 */
public final class HintIconEventEncoder extends EventEncoder<HintIconEvent> {

    @Override
    public GamePacket encode(HintIconEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(254);
	if (event.getVal() == 2) {
	    builder.put(DataType.BYTE, event.getType());
	    builder.put(DataType.SHORT, event.getId());
	    builder.put(DataType.TRI_BYTE, 0);
	} else if (event.getVal() == 1) {
	    builder.put(DataType.BYTE, event.getOrient());
	    builder.put(DataType.SHORT, event.getPos().getX());
	    builder.put(DataType.SHORT, event.getPos().getY());
	    builder.put(DataType.BYTE, event.getPos().getHeight());
	}
	return builder.toGamePacket();
    }
}
