package org.apollo.backend.method;

import org.apollo.backend.codec.FrontendPacket;

/**
 * An {@link MethodDecoder} decodes a {@link FrontendPacket} into an {@link Method} object which can be processed by
 * the server.
 * @param <E> The type of {@link Method}.
 * @author Steve
 */
public abstract class MethodDecoder<E extends Method> {

	/**
	 * Decodes the specified packet into an method.
	 * @param packet The packet.
	 * @return The method.
	 */
	public abstract E decode(FrontendPacket packet);
}
