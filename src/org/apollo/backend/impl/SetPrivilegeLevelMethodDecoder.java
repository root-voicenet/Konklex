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
		FrontendPacketReader reader = new FrontendPacketReader(packet);
		String player = reader.getString("player");
		int rights = reader.getInt("rights");
		String key = reader.getString("key");
		return new SetPrivilegeLevelMethod(player, rights, key);
	}

}