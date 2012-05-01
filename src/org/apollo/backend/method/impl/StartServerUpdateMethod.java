package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link StartServerUpdateMethodHandler}
 * @author Steve
 */
public final class StartServerUpdateMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server update method.
	 */
	public StartServerUpdateMethod() {
		super();
	}

	@Override
	public String getDisplay() {
		return "";
	}

	@Override
	public String getName() {
		return "startServerUpdate";
	}

	@Override
	public RequestMethod getRequested() {
		return request;
	}

	@Override
	public void setRequested(RequestMethod request) {
		this.request = request;
	}
}
