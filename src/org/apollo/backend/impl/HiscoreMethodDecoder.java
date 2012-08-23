package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.HiscoreMethod;

/**
 * An {@link MethodDecoder} for the {@link HiscoreMethod}
 * @author Steve
 */
public final class HiscoreMethodDecoder extends MethodDecoder<HiscoreMethod> {

	@Override
	public HiscoreMethod decode(FrontendPacket packet) {
		final FrontendPacketReader reader = new FrontendPacketReader(packet);
		final String user = reader.getString("user");
		return new HiscoreMethod(user);
	}

}
