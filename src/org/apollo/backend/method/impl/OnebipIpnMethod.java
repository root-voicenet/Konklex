package org.apollo.backend.method.impl;

import org.apollo.backend.codec.FrontendPacketReader;

/**
 * An {@link IpnMethod} for the onebip payment notifications.
 * @author Steve
 */
public final class OnebipIpnMethod extends IpnMethod {

    /**
     * Creates a new onebip ipn method.
     * @param packet The packet.
     */
    public OnebipIpnMethod(FrontendPacketReader packet) {
	super(packet);
    }

}
