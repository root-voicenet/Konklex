package org.apollo.api;

import org.apollo.api.method.MethodEncoder;
import org.apollo.api.method.impl.UpdateMethod;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MethodEncoder} for the {@link UpdateMethod}
 * @author Steve
 */
public final class UpdateMethodEncoder extends MethodEncoder<UpdateMethod> {

	@Override
	public GamePacket encode(UpdateMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(5);
		builder.put(DataType.SHORT, method.getTime());
		return builder.toGamePacket();
	}

}
