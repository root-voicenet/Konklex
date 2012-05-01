package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link GetGroupsMethodHandler}
 * @author Steve
 */
public final class GetGroupsMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server config method.
	 */
	public GetGroupsMethod() {
		super();
	}

	@Override
	public String getDisplay() {
		return "";
	}

	@Override
	public String getName() {
		return "getGroups";
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
