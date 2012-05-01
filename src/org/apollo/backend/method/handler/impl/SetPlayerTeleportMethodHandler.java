package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.SetPlayerTeleportMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.json.JSONException;

/**
 * An {@link FrontendHandler} for the {@link SetPlayerTeleportMethod}
 * @author Steve
 */
public class SetPlayerTeleportMethodHandler extends FrontendHandler<SetPlayerTeleportMethod> {

	private boolean error = false;

	@Override
	public Object handle(SetPlayerTeleportMethod method) throws JSONException {
		Player player = World.getWorld().getPlayer(method.getRequested().getArguments()[0]);
		String result = "";
		if (player != null) {
			player.teleport(new Position(Integer.parseInt(method.getRequested().getArguments()[1]), Integer.parseInt(method.getRequested().getArguments()[2])), false);
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
