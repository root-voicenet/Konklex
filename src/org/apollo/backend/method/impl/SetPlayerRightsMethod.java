package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link SetPlayerRightsMethod}
 * @author Steve
 */
public final class SetPlayerRightsMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server config method.
	 */
	public SetPlayerRightsMethod() {
		super(2);
	}

	@Override
	public String getDisplay() {
		return "Rights";
	}

	@Override
	public String getName() {
		return "setPlayerRights";
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
