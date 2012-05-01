package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.SetPlayerRightsMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.World;
import org.json.JSONException;

/**
 * An {@link FrontendHandler} for the {@link SetPlayerRightsMethod}
 * @author Steve
 */
public class SetPlayerRightsMethodHandler extends FrontendHandler<SetPlayerRightsMethod> {

	private boolean error = false;

	@Override
	public Object handle(SetPlayerRightsMethod method) throws JSONException {
		String result = "";
		Player player = World.getWorld().getPlayer(method.getRequested().getArguments()[0]);
		if (player != null) {
			player.setPrivilegeLevel(PrivilegeLevel.valueOf(Integer.parseInt(method.getRequested().getArguments()[1])));
			result = "Executed.";
			error = false;
		} else {
			result = method.getRequested().getArguments()[0] + " is offline.";
			error = true;
		}
		return result;
	}

	@Override
	public boolean isError() {
		return error;
	}
}
