package org.apollo.api.method.handler;

import org.apollo.api.method.handler.chain.MethodHandlerChain;

/**
 * Provides operations specific to an {@link MethodHandler} in an
 * {@link MethodHandlerChain}.
 * 
 * @author Steve
 */
public abstract class MethodHandlerContext {

	/**
	 * Breaks the handler chain.
	 */
	public abstract void breakHandlerChain();
}
