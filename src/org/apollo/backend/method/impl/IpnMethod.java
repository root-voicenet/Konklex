package org.apollo.backend.method.impl;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.method.Method;

/**
 * An {@link Method} for when a payment option is called upon.
 * @author Steve
 */
public abstract class IpnMethod extends Method {

    /**
     * The packet.
     */
    private final FrontendPacket packet;

    /**
     * Creates a new ipn method.
     * @param packet The packet containing the variables.
     */
    public IpnMethod(final FrontendPacket packet) {
	this.packet = packet;
    }

    /**
     * Gets the packet.
     * @return The packet.
     */
    public FrontendPacket getPacket() {
	return packet;
    }

}
