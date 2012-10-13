package org.apollo.net.release.r317;

import org.apollo.api.method.impl.LabelWorldMethod;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.MethodDecoder;

/**
 * An {@link MethodDecoder} for the {@link LabelWorldMethod}
 * @author Steve
 */
public final class LabelWorldMethodDecoder extends MethodDecoder<LabelWorldMethod> {

	@Override
	public LabelWorldMethod decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int world = (int) reader.getUnsigned(DataType.BYTE);
		return new LabelWorldMethod(world);
	}

}
