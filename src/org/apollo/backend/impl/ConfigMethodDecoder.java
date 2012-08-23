package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.ConfigMethod;

/**
 * An {@link MethodDecoder} for the {@link ConfigMethod}
 * @author Steve
 */
public final class ConfigMethodDecoder extends MethodDecoder<ConfigMethod> {

	@Override
	public ConfigMethod decode(FrontendPacket packet) {
		final FrontendPacketReader reader = new FrontendPacketReader(packet);
		final int id = reader.getInt("id");
		final int state = reader.getInt("state");
		final String key = reader.getString("key");
		return new ConfigMethod(key, id, state);
	}

}
