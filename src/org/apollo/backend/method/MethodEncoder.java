package org.apollo.backend.method;

import org.apollo.backend.codec.FrontendPacket;

/**
 * An {@link MethodEncoder} encodes {@link Method} objects into
 * {@link FrontendPacket} s which can be sent over the network.
 * @param <E> The type of {@link Method}.
 * @author Steve
 */
public abstract class MethodEncoder<E extends Method> {

    /**
     * Encodes the specified event into a packet.
     * @param method The method.
     * @return The packet.
     */
    public abstract FrontendPacket encode(E method);
}