package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.GetServerConfigMethod;
import org.apollo.game.model.Config;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link FrontendHandler} that handles {@link GetServerConfigMethod} requests.
 * @author Steve
 */
public class GetServerConfigMethodHandler extends FrontendHandler<GetServerConfigMethod> {

	@Override
	public JSONObject handle(GetServerConfigMethod method) throws JSONException {
		JSONObject builder = new JSONObject();
		builder.put("server-name", Config.SERVER_NAME);
		builder.put("server-whitelist", Config.SERVER_WHITELIST);
		builder.put("server-motd", Config.SERVER_MOTD);
		builder.put("server-motd-show", Config.SERVER_MOTD_SHOW);
		builder.put("server-login-show", Config.SERVER_LOGIN_SHOW);
		builder.put("server-access-denied", Config.SERVER_COMMAND_AD);
		builder.put("server-npcs", Config.SERVER_NPCS);
		builder.put("server-yell", Config.SERVER_YELL);
		return builder;
	}

	@Override
	public boolean isError() {
		return false;
	}
}
