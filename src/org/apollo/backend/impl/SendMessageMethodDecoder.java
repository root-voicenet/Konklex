package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.SendMessageMethod;

/**
 * An {@link MethodDecoder} for the {@link SendMessageMethod}
 * @author Steve
 */
public final class SendMessageMethodDecoder extends MethodDecoder<SendMessageMethod> {

	@Override
	public SendMessageMethod decode(FrontendPacket packet) {
		final FrontendPacketReader reader = new FrontendPacketReader(packet);
		final String player = reader.getString("player");
		final String message = reader.getString("message");
		final String key = reader.getString("key");
		return new SendMessageMethod(player, message, key);
	}

}
