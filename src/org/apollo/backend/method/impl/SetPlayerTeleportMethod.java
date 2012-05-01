package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link SetPlayerTeleportMethod}
 * @author Steve
 */
public final class SetPlayerTeleportMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server config method.
	 */
	public SetPlayerTeleportMethod() {
		super(3);
	}

	@Override
	public String getDisplay() {
		return "Teleport";
	}

	@Override
	public String getName() {
		return "setPlayerTeleport";
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
