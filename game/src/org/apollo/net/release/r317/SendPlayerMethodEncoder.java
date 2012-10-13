package org.apollo.net.release.r317;

import org.apollo.api.method.impl.SendPlayerMethod;
import org.apollo.game.model.World;
import org.apollo.net.codec.game.DataTransformation;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;
import org.apollo.net.release.MethodEncoder;

/**
 * An {@link MethodEncoder} for the {@link SendPlayerMethod}
 * @author Steve
 */
public final class SendPlayerMethodEncoder extends MethodEncoder<SendPlayerMethod> {

	@Override
	public GamePacket encode(SendPlayerMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(2);
		builder.put(DataType.LONG, DataTransformation.QUADRUPLE, method.getPlayer());
		builder.put(DataType.BYTE, method.getRights());
		builder.put(DataType.BYTE, method.isOnline() ? 1 : 0);
		builder.put(DataType.BYTE, World.getWorld().getId());
		return builder.toGamePacket();
	}

}
