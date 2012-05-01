package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.SetPlayerKickMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.json.JSONException;

/**
 * An {@link FrontendHandler} for the {@link SetPlayerKickMethod}
 * @author Steve
 */
public class SetPlayerKickMethodHandler extends FrontendHandler<SetPlayerKickMethod> {

	private boolean error = false;

	@Override
	public Object handle(SetPlayerKickMethod method) throws JSONException {
		String result = "";
		Player player = World.getWorld().getPlayer(method.getRequested().getArguments()[0]);
		if (player != null) {
			player.logout();
			error = false;
			result = "Executed.";
		} else {
			error = true;
			result = method.getRequested().getArguments()[0] + " is offline.";
		}
		return result;
	}

	@Override
	public boolean isError() {
		return error;
	}
	/**
	 * Player player = api.getPlayer(method.getArguments()[0]); if (player != null) { player.logout(); error = false; result = "Executed."; } else { error = true; result =
	 * method.getArguments()[0]+" is offline."; }
	 */
}
