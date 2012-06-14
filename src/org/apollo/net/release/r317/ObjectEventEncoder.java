package org.apollo.net.release.r317;

import org.apollo.game.event.impl.ObjectEvent;
import org.apollo.game.model.GameObject;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SecondObjectEvent}.
 * @author Steve
 */
public final class ObjectEventEncoder extends EventEncoder<ObjectEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
     */
    @Override
    public GamePacket encode(ObjectEvent event) {
	final GameObject object = event.getObject();
	final GamePacketBuilder builder = new GamePacketBuilder(object.isRemoving() ? 101 : 151);
	if (object.isRemoving()) {
	    builder.put(DataType.BYTE, DataTransformation.NEGATE, (object.getType() << 2) + (object.getRotation() & 3));
	    builder.put(DataType.BYTE, 0);
	} else {
	    builder.put(DataType.BYTE, DataTransformation.ADD, 0);
	    builder.put(DataType.SHORT, DataOrder.LITTLE, object.getDefinition().getId());
	    builder.put(DataType.BYTE, DataTransformation.SUBTRACT, (object.getType() << 2)
		    + (object.getRotation() & 3));
	}
	return builder.toGamePacket();
    }
}