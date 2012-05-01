package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.impl.GetLatestUpdateMethodHandler;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link GetLatestUpdateMethodHandler}
 * @author Steve
 */
public final class GetLatestUpdateMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server config method.
	 */
	public GetLatestUpdateMethod() {
		super();
	}

	@Override
	public String getDisplay() {
		return "";
	}

	@Override
	public String getName() {
		return "getLatest";
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
