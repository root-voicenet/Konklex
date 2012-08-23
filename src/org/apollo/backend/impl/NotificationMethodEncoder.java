package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketBuilder;
import org.apollo.backend.method.MethodEncoder;
import org.apollo.backend.method.impl.NotificationMethod;

/**
 * An {@link MethodEncoder} for the {@link NotificationMethod}
 * @author Steve
 */
public final class NotificationMethodEncoder extends MethodEncoder<NotificationMethod> {

	@Override
	public FrontendPacket encode(NotificationMethod method) {
		final FrontendPacketBuilder builder = new FrontendPacketBuilder("register");
		builder.addParameter("event", method.getKey());
		builder.addParameter("data", method.getValue());
		return builder.toFrontendPacket();
	}
}
