package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link SetPlayerKickMethod}
 * @author Steve
 */
public final class SetPlayerKickMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new yell method.
	 * @param size The size of the required arguments.
	 */
	public SetPlayerKickMethod() {
		super(1);
	}

	@Override
	public String getDisplay() {
		return "Kick";
	}

	@Override
	public String getName() {
		return "setPlayerKick";
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
