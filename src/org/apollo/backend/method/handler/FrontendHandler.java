package org.apollo.backend.method.handler;

import org.apollo.backend.method.Method;
import org.json.JSONException;

/**
 * A class which handles frontend methods.
 * @param <E> The type of event this class handles.
 * @author Steve
 */
public abstract class FrontendHandler<E extends Method> {

	/**
	 * Handles an method.
	 * @param method The method requested.
	 * @return An object, preferable with {@code toString()} included.
	 * @throws JSONException If an exception occurs during a JSON handle.
	 */
	public abstract Object handle(E method) throws JSONException;

	/**
	 * Flag for error.
	 * @return True if error, false if not.
	 */
	public abstract boolean isError();
}
