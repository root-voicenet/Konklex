package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.SetPrivilegeLevelMethod;

/**
 * An {@link MethodDecoder} for the {@link SetPrivilegeLevelMethod}
 * @author Steve
 */
public final class SetPrivilegeLevelMethodDecoder extends MethodDecoder<SetPrivilegeLevelMethod> {

	@Override
	public SetPrivilegeLevelMethod decode(FrontendPacket packet) {
		final FrontendPacketReader reader = new FrontendPacketReader(packet);
		final String player = reader.getString("player");
		final int rights = reader.getInt("rights");
		final String key = reader.getString("key");
		return new SetPrivilegeLevelMethod(player, rights, key);
	}

}