package org.apollo.net.release.r317;

import org.apollo.api.method.impl.SendPlayerMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.MethodDecoder;

/**
 * An {@link MethodDecoder} for the {@link SendPlayerMethod}
 * @author Steve
 */
public final class ReceivePlayerMethodDecoder extends MethodDecoder<SendPlayerMethod> {

	@Override
	public SendPlayerMethod decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		long player = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
		int status = (int) reader.getUnsigned(DataType.BYTE);
		int world = (int) reader.getUnsigned(DataType.BYTE);
		return new SendPlayerMethod(player, status == 1 ? true : false, world);
	}

}
