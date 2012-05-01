package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.GetGroupsMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link FrontendHandler} that handles {@link GetGroupsMethod} requests.
 * @author Steve
 */
public class GetGroupsMethodHandler extends FrontendHandler<GetGroupsMethod> {

	@Override
	public Object handle(GetGroupsMethod method) throws JSONException {
		JSONArray builder = new JSONArray();
		for (PrivilegeLevel level : Player.PrivilegeLevel.values()) {
			JSONObject result = new JSONObject();
			result.put("name", level.toString());
			result.put("level", level.toInteger());
			builder.put(result);
		}
		return builder;
	}

	@Override
	public boolean isError() {
		return false;
	}
}
