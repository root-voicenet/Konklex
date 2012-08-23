package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.codec.FrontendPacketBuilder;
import org.apollo.backend.method.MethodEncoder;
import org.apollo.backend.method.impl.ResponseMethod;

/**
 * An {@link MethodEncoder} for the {@link ResponseMethod}
 * @author Steve
 */
public final class ResponseMethodEncoder extends MethodEncoder<ResponseMethod> {

	@Override
	public FrontendPacket encode(ResponseMethod method) {
		final FrontendPacketBuilder builder = new FrontendPacketBuilder(method.getRequestedMethod());
		builder.addParameter("response", method.getResponseMessage());
		builder.setError(method.isError());
		return builder.toFrontendPacket();
	}
}
