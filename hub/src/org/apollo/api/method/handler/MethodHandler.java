package org.apollo.api.method.handler;

import org.apollo.api.method.Method;
import org.apollo.net.session.ProxyApiSession;

/**
 * A class which handles methods.
 * @param <E> The type of method this class handles.
 * @author Steve
 */
public abstract class MethodHandler<E extends Method> {

	/**
	 * Handles an event.
	 * @param ctx The context.
	 * @param session The session.
	 * @param method The method.
	 */
	public abstract void handle(MethodHandlerContext ctx, ProxyApiSession session, E method);
}