package org.apollo.api;

import org.apollo.api.method.MethodEncoder;
import org.apollo.api.method.impl.SendFriendMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MethodEncoder} for the {@link SendFriendMethod}
 * @author Steve
 */
public final class SendFriendMethodEncoder extends MethodEncoder<SendFriendMethod> {

	@Override
	public GamePacket encode(SendFriendMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(2);
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getFriend());
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getSender());
		builder.put(DataType.BYTE, method.getStatus());
		return builder.toGamePacket();
	}

}
