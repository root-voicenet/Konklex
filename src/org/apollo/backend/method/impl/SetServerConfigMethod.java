package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.impl.SetServerConfigMethodHandler;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link SetServerConfigMethodHandler}
 * @author Steve
 */
public final class SetServerConfigMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server config method.
	 */
	public SetServerConfigMethod() {
		super(2);
	}

	@Override
	public String getDisplay() {
		return "";
	}

	@Override
	public String getName() {
		return "setConfig";
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
