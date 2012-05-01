package org.apollo.backend.method.handler.impl;

import org.apollo.backend.Manager;
import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.GetFrontendInformationMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link FrontendHandler} that handles {@link GetFrontendInformationMethod} requests.
 * @author Steve
 */
public class GetFrontendInformationMethodHandler extends FrontendHandler<GetFrontendInformationMethod> {

	@Override
	public JSONArray handle(GetFrontendInformationMethod method) throws JSONException {
		JSONArray builder = new JSONArray();
		for (Method methodz : Manager.getInstance().getMethods()) {
			if (methodz.getName().contains("Player")) {
				JSONObject create = new JSONObject();
				create.put("name", methodz.getName());
				create.put("args", methodz.getSize());
				create.put("display", methodz.getDisplay());
				builder.put(create);
			}
		}
		return builder;
	}

	@Override
	public boolean isError() {
		return false;
	}
}
