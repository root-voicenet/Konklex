package org.apollo.net.release;

import org.apollo.api.method.Method;
import org.apollo.net.codec.game.GamePacket;

/**
 * An {@link MethodDecoder} decodes a {@link FrontendPacket} into an {@link Method} object which can be processed by the
 * server.
 * @param <E> The type of {@link Method}.
 * @author Steve
 */
public abstract class MethodDecoder<E extends Method> {

	/**
	 * Decodes the specified packet into an method.
	 * @param packet The packet.
	 * @return The method.
	 */
	public abstract E decode(GamePacket packet);
}
