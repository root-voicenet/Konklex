package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.SetPositionMethod;
import org.apollo.game.model.Position;

/**
 * An {@link MethodDecoder} for the {@link SetPositionMethod}
 * @author Steve
 */
public final class SetPositionMethodDecoder extends MethodDecoder<SetPositionMethod> {

    @Override
    public SetPositionMethod decode(FrontendPacket packet) {
	final FrontendPacketReader reader = new FrontendPacketReader(packet);
	final String player = reader.getString("player");
	final Position position = new Position(reader.getInt("x"), reader.getInt("y"));
	final String key = reader.getString("key");
	return new SetPositionMethod(player, position, key);
    }

}
