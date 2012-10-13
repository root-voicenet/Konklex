package org.apollo.api;

import org.apollo.api.method.MethodEncoder;
import org.apollo.api.method.impl.LabelWorldMethod;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketBuilder;

/**
 * An {@link MethodEncoder} for the {@link LabelWorldMethod}
 * @author Steve
 */
public final class LabelWorldMethodEncoder extends MethodEncoder<LabelWorldMethod> {

	@Override
	public GamePacket encode(LabelWorldMethod method) {
		GamePacketBuilder builder = new GamePacketBuilder(3);
		builder.put(DataType.BYTE, method.getWorld());
		return builder.toGamePacket();
	}

}
