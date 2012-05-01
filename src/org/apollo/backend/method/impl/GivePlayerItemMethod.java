package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.impl.GivePlayerItemMethodHandler;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link GivePlayerItemMethodHandler}
 * @author Steve
 */
public final class GivePlayerItemMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new server config method.
	 */
	public GivePlayerItemMethod() {
		super(4);
	}

	@Override
	public String getDisplay() {
		return "Give Item";
	}

	@Override
	public String getName() {
		return "givePlayerItem";
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
