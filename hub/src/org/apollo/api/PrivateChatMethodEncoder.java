package org.apollo.api;

import org.apollo.api.method.MethodEncoder;
import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;

/**
 * An {@link MethodEncoder} for the {@link PrivateChatMethod}
 * @author Steve
 */
public final class PrivateChatMethodEncoder extends MethodEncoder<PrivateChatMethod> {

	@Override
	public GamePacket encode(PrivateChatMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(1, PacketType.VARIABLE_BYTE);
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getFriend());
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getSender());
		builder.put(DataType.BYTE, method.getLevel());
		builder.putBytes(method.getMessage());
		return builder.toGamePacket();
	}

}
