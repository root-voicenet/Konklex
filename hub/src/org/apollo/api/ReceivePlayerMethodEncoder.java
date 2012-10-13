package org.apollo.api;

import org.apollo.api.method.MethodEncoder;
import org.apollo.api.method.impl.ReceivePlayerMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MethodEncoder} for the {@link ReceivePlayerMethod}
 * @author Steve
 */
public final class ReceivePlayerMethodEncoder extends MethodEncoder<ReceivePlayerMethod> {

	@Override
	public GamePacket encode(ReceivePlayerMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(4);
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getPlayer());
		builder.put(DataType.BYTE, method.getStatus());
		builder.put(DataType.BYTE, method.getWorld());
		return builder.toGamePacket();
	}

}
