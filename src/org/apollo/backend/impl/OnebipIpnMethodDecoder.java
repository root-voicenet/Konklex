package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.OnebipIpnMethod;

/**
 * An {@link MethodDecoder} for the {@link OnebipIpnMethod}
 * 
 * @author Steve
 */
public final class OnebipIpnMethodDecoder extends
MethodDecoder<OnebipIpnMethod> {

	@Override
	public OnebipIpnMethod decode(final FrontendPacket packet) {
		return new OnebipIpnMethod(packet);
	}

}
