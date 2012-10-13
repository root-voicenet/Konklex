package org.apollo.net.release.r317;

import org.apollo.api.method.impl.UpdateMethod;
import org.apollo.net.codec.game.DataType;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.codec.game.GamePacketReader;
import org.apollo.net.release.MethodDecoder;

/**
 * An {@link MethodDecoder} for the {@link UpdateMethod}
 * @author Steve
 */
public final class UpdateMethodDecoder extends MethodDecoder<UpdateMethod> {

	@Override
	public UpdateMethod decode(GamePacket packet) {
		GamePacketReader reader = new GamePacketReader(packet);
		int time = (int) reader.getSigned(DataType.SHORT);
		return new UpdateMethod(time);
	}

}
