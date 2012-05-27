package org.apollo.backend.method;

import org.apollo.backend.Frontend;

/**
 * An {@link Frontend} method.
 * @author Steve
 */
public abstract class Method {

    /**
     * The key that was requested.
     */
    private final String key;

    /**
     * Creates the method.
     */
    public Method() {
	this("");
    }

    /**
     * Creates the method.
     * @param key The key that was requested.
     */
    public Method(String key) {
	this.key = key;
    }

    /**
     * Gets the key that was requested.
     * @return The key that was requested.
     */
    public String getKey() {
	return key;
    }

}