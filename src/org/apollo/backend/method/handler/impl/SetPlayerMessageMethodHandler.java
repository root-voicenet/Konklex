package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.SetPlayerMessageMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.json.JSONException;

/**
 * An {@link FrontendHandler} that handles {@link SetPlayerMessageMethod} requests.
 * @author Steve
 */
public class SetPlayerMessageMethodHandler extends FrontendHandler<SetPlayerMessageMethod> {

	private boolean error = false;

	@Override
	public Object handle(SetPlayerMessageMethod method) throws JSONException {
		String result = "";
		Player player = World.getWorld().getPlayer(method.getRequested().getArguments()[0]);
		if (player != null) {
			player.sendMessage(method.getRequested().getArguments()[1]);
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
