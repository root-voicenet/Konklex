package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.SendGlobalYellMethod;
import org.apollo.backend.util.Notification;
import org.apollo.backend.util.Notification.Type;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link FrontendHandler} for the {@link SendGlobalYellMethod}
 * @author Steve
 */
public final class SendGlobalYellMethodHandler extends FrontendHandler<SendGlobalYellMethod> {

	@Override
	public JSONObject handle(SendGlobalYellMethod method) throws JSONException {
		String yell = method.getRequested().getArguments()[0];
		for (Player player : World.getWorld().getPlayerRepository()) {
			player.sendMessage("[Owner] Server: " + yell);
			Notification.getInstance().add("Server", yell, Type.MESSAGE);
		}
		return new JSONObject().put("yell", yell);
	}

	@Override
	public boolean isError() {
		return false;
	}
}
