package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.SetServerConfigMethod;
import org.apollo.game.model.Config;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link FrontendHandler} that handles {@link SetServerConfigMethod} requests.
 * @author Steve
 */
public class SetServerConfigMethodHandler extends FrontendHandler<SetServerConfigMethod> {

	@Override
	public JSONObject handle(SetServerConfigMethod method) throws JSONException {
		String key = method.getRequested().getArguments()[0];
		String value = method.getRequested().getArguments()[1];
		if (key.equals("server-name")) {
			Config.SERVER_NAME = value;
		} else
			if (key.equals("server-whitelist")) {
				Config.SERVER_WHITELIST = Boolean.parseBoolean(value);
			} else
				if (key.equals("server-motd")) {
					Config.SERVER_MOTD = value.replace("_", " ");
				} else
					if (key.equals("server-motd-show")) {
						Config.SERVER_MOTD_SHOW = Boolean.parseBoolean(value);
					} else
						if (key.equals("server-login-show")) {
							Config.SERVER_LOGIN_SHOW = Boolean.parseBoolean(value);
						} else
							if (key.equals("server-access-denied")) {
								Config.SERVER_COMMAND_AD = Boolean.parseBoolean(value);
							} else
								if (key.equals("server-npcs")) {
									Config.SERVER_NPCS = Boolean.parseBoolean(value);
								} else
									if (key.equals("server-yell")) {
										Config.SERVER_YELL = Boolean.parseBoolean(value);
									}
		return new JSONObject();
	}

	@Override
	public boolean isError() {
		return false;
	}
}
