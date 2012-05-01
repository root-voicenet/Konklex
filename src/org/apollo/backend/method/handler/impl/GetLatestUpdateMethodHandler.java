package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.GetLatestUpdateMethod;
import org.json.JSONException;

/**
 * An {@link FrontendHandler} that handles {@link GetLatestUpdateMethod} requests.
 * @author Steve
 */
public class GetLatestUpdateMethodHandler extends FrontendHandler<GetLatestUpdateMethod> {

	@Override
	public Object handle(GetLatestUpdateMethod method) throws JSONException {
		return "Updated the global object manager, rewriting majority of the base.";
	}

	@Override
	public boolean isError() {
		return false;
	}
}
