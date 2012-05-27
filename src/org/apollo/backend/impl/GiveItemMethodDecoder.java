package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.GiveItemMethod;

/**
 * An {@link MethodDecoder} for the {@link GiveItemMethod}
 * @author Steve
 */
public final class GiveItemMethodDecoder extends MethodDecoder<GiveItemMethod> {

	@Override
	public GiveItemMethod decode(FrontendPacket packet) {
		FrontendPacketReader reader = new FrontendPacketReader(packet);
		String player = reader.getString("player");
		int item = reader.getInt("item");
		int amount = reader.getInt("amount");
		String key = reader.getString("key");
		return new GiveItemMethod(player, item, amount, key);
	}

}
