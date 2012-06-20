package org.apollo.backend.method.impl;

import org.apollo.backend.codec.FrontendPacketReader;

/**
 * An {@link IpnMethod} for the paypal payment notifications.
 * @author Steve
 */
public final class PaypalIpnMethod extends IpnMethod {

    /**
     * Creates a new paypal ipn method.
     * @param packet The packet.
     */
    public PaypalIpnMethod(FrontendPacketReader packet) {
	super(packet);
    }

}
