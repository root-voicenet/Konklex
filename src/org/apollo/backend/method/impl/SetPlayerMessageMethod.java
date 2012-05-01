package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link GetPlayerMessageMethodHandler}
 * @author Steve
 */
public final class SetPlayerMessageMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server config method.
	 */
	public SetPlayerMessageMethod() {
		super(2);
	}

	@Override
	public String getDisplay() {
		return "Message";
	}

	@Override
	public String getName() {
		return "setPlayerMessage";
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
