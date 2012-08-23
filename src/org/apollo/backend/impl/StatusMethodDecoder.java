package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.StatusMethod;

/**
 * An {@link MethodDecoder} for the {@link StatusMethod}
 * @author Steve
 */
public final class StatusMethodDecoder extends MethodDecoder<StatusMethod> {

	@Override
	public StatusMethod decode(FrontendPacket packet) {
		return new StatusMethod();
	}
}