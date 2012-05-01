package org.apollo.backend.method;

import org.apollo.backend.util.RequestMethod;

/**
 * Create a new method.
 * @author Steve
 */
public abstract class Method {

	/**
	 * The size of the required arguments.
	 */
	private final int size;

	/**
	 * Create a new method with no required arguments.
	 */
	public Method() {
		this.size = 0;
	}

	/**
	 * Create a new method.
	 * @param size The size of the arguments.
	 */
	public Method(int size) {
		this.size = size;
	}

	/**
	 * Gets the display name of the method.
	 * @return The display name.
	 */
	public abstract String getDisplay();

	/**
	 * Gets the real method name.
	 * @return The method name.
	 */
	public abstract String getName();

	/**
	 * Gets the requested method.
	 * @return The requested method.
	 */
	public abstract RequestMethod getRequested();

	/**
	 * Get the required parameter size.
	 * @return {@link Integer} The size.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Set the requested method.
	 * @param request The requested method.
	 */
	public abstract void setRequested(RequestMethod request);
}
