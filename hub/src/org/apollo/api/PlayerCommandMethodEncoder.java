package org.apollo.api;

import org.apollo.api.method.MethodEncoder;
import org.apollo.api.method.impl.PlayerCommandMethod;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.meta.PacketType;

/**
 * An {@link MethodEncoder} for the {@link PlayerCommandMethod}
 * @author Steve
 */
public final class PlayerCommandMethodEncoder extends MethodEncoder<PlayerCommandMethod> {

	@Override
	public GamePacket encode(PlayerCommandMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(6, PacketType.VARIABLE_BYTE);
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getPlayer());
		builder.putBytes(method.getCommand());
		return builder.toGamePacket();
	}

}
