package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SetInterfaceTextEvent;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SetInterfaceTextEvent}.
 * @author Graham
 */
public final class SetInterfaceTextEventEncoder extends EventEncoder<SetInterfaceTextEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
     */
    @Override
    public GamePacket encode(SetInterfaceTextEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(126, PacketType.VARIABLE_SHORT);
	builder.putString(event.getText());
	builder.put(DataType.SHORT, DataTransformation.ADD, event.getInterfaceId());
	return builder.toGamePacket();
    }
}