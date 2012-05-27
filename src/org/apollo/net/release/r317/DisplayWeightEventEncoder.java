package org.apollo.net.release.r317;

import org.apollo.game.event.impl.DisplayWeightEvent;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link DisplayWeightEvent}
 * @author Steve
 */
public final class DisplayWeightEventEncoder extends EventEncoder<DisplayWeightEvent> {

    @Override
    public GamePacket encode(DisplayWeightEvent event) {
	final GamePacketBuilder builder = new GamePacketBuilder(240);
	builder.put(DataType.SHORT, event.getWeight());
	return builder.toGamePacket();
    }
}
