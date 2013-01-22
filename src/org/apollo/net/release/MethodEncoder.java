package org.apollo.net.release;

import org.apollo.api.method.Method;
import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link MethodEncoder} encodes {@link Method} objects into {@link GamePacket} s which can be sent over the
 * network.
 * @param <E> The type of {@link Method}.
 * @author Steve
 */
public abstract class MethodEncoder<E extends Method> {

	/**
	 * Encodes the specified event into a packet.
	 * @param method The method.
	 * @return The packet.
	 */
	public abstract GamePacket encode(E method);
}