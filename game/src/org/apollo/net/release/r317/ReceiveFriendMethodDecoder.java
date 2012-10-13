package org.apollo.net.release.r317;

import org.apollo.api.method.impl.ReceiveFriendMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.MethodDecoder;

/**
 * An {@link MethodDecoder} for the {@link ReceiveFriendMethod}
 * @author Steve
 */
public final class ReceiveFriendMethodDecoder extends MethodDecoder<ReceiveFriendMethod> {

	@Override
	public ReceiveFriendMethod decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		long friend = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
		long sender = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
		int status = (int) reader.getUnsigned(DataType.BYTE);
		return new ReceiveFriendMethod(sender, friend, status);
	}

}
