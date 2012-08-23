package org.apollo.backend;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.method.Method;

/**
 * An {@link FrontendEncoder} encodes {@link Method} objects into
 * {@link FrontendPacket} s which can be sent over the network.
 * @param <E> The type of {@link MethodT}.
 * @author Steve
 */
public abstract class FrontendEncoder<E extends Method> {

	/**
	 * Encodes the specified event into a packet.
	 * @param method The method.
	 * @return The packet.
	 */
	public abstract FrontendPacket encode(E method);
}