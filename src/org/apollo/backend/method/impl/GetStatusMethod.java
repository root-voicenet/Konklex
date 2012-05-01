package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.impl.GetStatusMethodHandler;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link GetStatusMethodHandler}
 * @author Steve
 */
public final class GetStatusMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server config method.
	 */
	public GetStatusMethod() {
		super(1);
	}

	@Override
	public String getDisplay() {
		return "";
	}

	@Override
	public String getName() {
		return "getStatus";
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
