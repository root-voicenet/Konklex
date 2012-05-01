package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.impl.GetMessagesMethodHandler;
import org.apollo.backend.util.RequestMethod;

/**
 * An {@link Method} for the {@link GetMessagesMethodHandler}
 * @author Steve
 */
public final class GetMessagesMethod extends Method {

	private RequestMethod request;

	/**
	 * Create a new get messages method.
	 */
	public GetMessagesMethod() {
		super(1);
	}

	@Override
	public String getDisplay() {
		return "";
	}

	@Override
	public String getName() {
		return "getMessages";
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
