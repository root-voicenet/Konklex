package org.apollo.api.method.handler.chain;

import java.util.Map;

import org.apollo.api.method.Method;

/**
 * A group of {@link MethodHandlerChain}s classified by the {@link Method} type.
 * @author Steve
 */
public final class MethodHandlerChainGroup {

	/**
	 * The map of method classes to method handler chains.
	 */
	private final Map<Class<? extends Method>, MethodHandlerChain<?>> chains;

	/**
	 * Creates the method handler chain group.
	 * @param chains The chains map.
	 */
	public MethodHandlerChainGroup(Map<Class<? extends Method>, MethodHandlerChain<?>> chains) {
		this.chains = chains;
	}

	/**
	 * Gets an {@link MethodHandlerChain} from this group.
	 * @param <E> The type of method.
	 * @param clazz The event class.
	 * @return The {@link MethodHandlerChain} if one was found, {@code null} otherwise.
	 */
	@SuppressWarnings("unchecked")
	public <E extends Method> MethodHandlerChain<E> getChain(Class<E> clazz) {
		return (MethodHandlerChain<E>) chains.get(clazz);
	}
}
