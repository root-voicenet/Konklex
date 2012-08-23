package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.SendYellMethod;

/**
 * An {@link MethodDecoder} for the {@link SendYellMethod}
 * @author Steve
 */
public final class SendYellMethodDecoder extends MethodDecoder<SendYellMethod> {

	@Override
	public SendYellMethod decode(FrontendPacket packet) {
		final FrontendPacketReader reader = new FrontendPacketReader(packet);
		final String message = reader.getString("message");
		final String key = reader.getString("key");
		return new SendYellMethod(message, key);
	}

}