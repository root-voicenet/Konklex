package org.apollo.backend;

import org.apollo.backend.codec.FrontendPacket;
import org.apollo.backend.method.Method;

/**
 * An {@link FrontendDecoder} decodes a {@link FrontendPacket} into an
 * {@link Method} object which can be processed by the server.
 * 
 * @param <E>
 *            The type of {@link Method}.
 * @author Steve
 */
public abstract class FrontendDecoder<E extends Method> {

	/**
	 * Decodes the specified packet into an method.
	 * 
	 * @param packet
	 *            The packet.
	 * @return The method.
	 */
	public abstract E decode(FrontendPacket packet);
}
