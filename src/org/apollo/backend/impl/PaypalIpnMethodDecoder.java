package org.apollo.backend.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.backend.method.impl.PaypalIpnMethod;

/**
 * An {@link MethodDecoder} for the {@link PaypalIpnMethod}
 * @author Steve
 */
public final class PaypalIpnMethodDecoder extends MethodDecoder<PaypalIpnMethod> {

    @Override
    public PaypalIpnMethod decode(final FrontendPacket packet) {
	return new PaypalIpnMethod(packet);
    }

}
