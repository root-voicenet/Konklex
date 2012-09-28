package org.apollo.net.release.r317;

import org.apollo.api.method.impl.TimeMethod;
import org.apollo.game.model.World;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.MethodEncoder;

/**
 * An {@link MethodEncoder} for the {@link TimeMethod}
 * @author Steve
 */
public final class TimeMethodEncoder extends MethodEncoder<TimeMethod> {

	@Override
	public GamePacket encode(TimeMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(4);
		builder.put(DataType.BYTE, World.getWorld().getId());
		builder.put(DataType.INT, method.getTime());
		return builder.toGamePacket();
	}

}
