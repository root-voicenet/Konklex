package org.apollo.net.release.r317;

import org.apollo.game.event.Event;
import org.apollo.game.event.impl.RegionUpdateEvent;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.EventEncoder;

/**
 * An {@link EventEncoder} for the {@link RegionUpdateEvent}
 * @author Steve
 */
public final class RegionUpdateEventEncoder extends EventEncoder<RegionUpdateEvent> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public GamePacket encode(RegionUpdateEvent event) {
		GamePacketBuilder builder = new GamePacketBuilder(60, PacketType.VARIABLE_SHORT);
		final Position base = event.getBase(), pos = event.getPosition();
		builder.put(DataType.BYTE, pos.getLocalY(base));
		builder.put(DataType.BYTE, DataTransformation.NEGATE, pos.getLocalX(base));
		for (Event e : event.getEvents()) {
			final EventEncoder encoder = World.getWorld().getContext().getRelease().getEventEncoder(e.getClass());
			final GamePacket packet = encoder.encode(e);
			builder.put(DataType.BYTE, packet.getOpcode());
			builder.putBytes(packet.getPayload());
		}
		return builder.toGamePacket();
	}

}
