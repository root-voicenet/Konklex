package org.apollo.net.release.r317;

import org.apollo.game.event.impl.GroundItemEvent;
import org.apollo.game.model.GroundItem;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link GroundItemEventEncoder}.
 * @author Arrowzftw
 */
public final class GroundItemEncoder extends EventEncoder<GroundItemEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
     */
    @Override
    public GamePacket encode(GroundItemEvent event) {
	final GroundItem item = event.getGroundItem();
	final GamePacketBuilder builder = new GamePacketBuilder(item.isRemoving() ? 156 : 44);
	if (item.isRemoving()) {
	    builder.put(DataType.BYTE, DataTransformation.SUBTRACT, 0);
	    builder.put(DataType.SHORT, item.getItem().getId());
	    return builder.toGamePacket();
	} else {
	    builder.put(DataType.SHORT, DataOrder.LITTLE, DataTransformation.ADD, item.getItem().getId());
	    builder.put(DataType.SHORT, item.getItem().getAmount());
	    builder.put(DataType.BYTE, 0);
	}
	return builder.toGamePacket();
    }
}