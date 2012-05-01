package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link SendGlobalYellMethod}
 * @author Steve
 */
public final class SendGlobalYellMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new yell method.
	 * @param size The size of the required arguments.
	 */
	public SendGlobalYellMethod() {
		super(1);
	}

	@Override
	public String getDisplay() {
		return "";
	}

	@Override
	public String getName() {
		return "sendYell";
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
