package org.apollo.backend.method.handler;

import org.apollo.backend.codec.session.FrontendChannel;
import org.apollo.backend.method.Method;

/**
 * A class which handles methods.
 * @param <E> The type of method this class handles.
 * @author Steve
 */
public abstract class MethodHandler<E extends Method> {

	/**
	 * Handles an event.
	 * @param ctx The context.
	 * @param channel The frontend channel.
	 * @param method The method.
	 */
	public abstract void handle(MethodHandlerContext ctx, FrontendChannel channel, E method);
}