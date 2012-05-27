package org.apollo.backend.method.handler;

import org.apollo.backend.method.handler.chain.MethodHandlerChain;

/**
 * Provides operations specific to an {@link MethodHandler} in an {@link MethodHandlerChain}.
 * @author Steve
 */
public abstract class MethodHandlerContext {

	/**
	 * Breaks the handler chain.
	 */
	public abstract void breakHandlerChain();

	/**
	 * Gets the requested method.
	 * @return The requested method.
	 */
	public abstract String getRequested();
}
