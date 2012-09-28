package org.apollo.net.release.r317;

import org.apollo.api.method.impl.SendFriendMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.MethodEncoder;

/**
 * An {@link MethodEncoder} for the {@link SendFriendMethod}
 * @author Steve
 */
public final class SendFriendMethodEncoder extends MethodEncoder<SendFriendMethod> {

	@Override
	public GamePacket encode(SendFriendMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(1);
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getFriend());
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getSender());
		return builder.toGamePacket();
	}

}
