package org.apollo.net.release.r317;

import org.apollo.game.event.impl.SoundEvent;
import org.apollo.net.codec.game.DataOrder;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link SoundEvent}.
 * @author Steve
 */
public final class SoundEventEncoder extends EventEncoder<SoundEvent> {

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.net.release.EventEncoder#encode(org.apollo.game.event.Event)
     */
    @Override
    public GamePacket encode(SoundEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(event.isTemp() ? 121 : 74);
	if (event.isTemp()) {
	    builder.put(DataType.SHORT, DataOrder.LITTLE, event.getSound());
	    builder.put(DataType.SHORT, DataOrder.LITTLE, event.getLastSound());
	} else
	    builder.put(DataType.SHORT, event.getSound());
	return builder.toGamePacket();
    }
}
