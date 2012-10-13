package org.apollo.api;

import org.apollo.api.method.MethodDecoder;
import org.apollo.api.method.impl.ReceivePlayerMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;

/**
 * An {@link MethodDecoder} for the {@link ReceivePlayerMethod}
 * @author Steve
 */
public final class ReceivePlayerMethodDecoder extends MethodDecoder<ReceivePlayerMethod> {

	@Override
	public ReceivePlayerMethod decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		long player = reader.getSigned(DataType.LONG, DataTransformation.QUADRUPLE);
		int rights = (int) reader.getUnsigned(DataType.BYTE);
		int status = (int) reader.getUnsigned(DataType.BYTE);
		int world = (int) reader.getUnsigned(DataType.BYTE);
		return new ReceivePlayerMethod(player, rights, status, world);
	}

}
