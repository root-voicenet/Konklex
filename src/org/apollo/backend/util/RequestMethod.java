package org.apollo.backend.util;

/**
 * A utility for sending a method to the {@link Dispatcher}.
 * @author Steve
 */
public final class RequestMethod {

	/**
	 * The method.
	 */
	private final String method;

	/**
	 * The arguments.
	 */
	private final String[] arguments;

	/**
	 * Instance the method.
	 * @param name the name
	 * @param args the args
	 */
	public RequestMethod(String name, String[] args) {
		this.method = name;
		this.arguments = args;
	}

	/**
	 * Get the requested method arguments.
	 * @return {@link String}
	 */
	public final String[] getArguments() {
		return arguments;
	}

	/**
	 * Get the requested method.
	 * @return {@link String}
	 */
	public final String getMethod() {
		return method;
	}
}
