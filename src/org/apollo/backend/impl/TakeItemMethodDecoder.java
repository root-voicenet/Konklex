package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketReader;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.TakeItemMethod;

/**
 * An {@link MethodDecoder} for the {@link TakeItemMethod}
 * @author Steve
 */
public final class TakeItemMethodDecoder extends MethodDecoder<TakeItemMethod> {

    @Override
    public TakeItemMethod decode(FrontendPacket packet) {
	final FrontendPacketReader reader = new FrontendPacketReader(packet);
	final String player = reader.getString("player");
	final int item = reader.getInt("item");
	final int amount = reader.getInt("amount");
	final String key = reader.getString("key");
	return new TakeItemMethod(player, item, amount, key);
    }

}
