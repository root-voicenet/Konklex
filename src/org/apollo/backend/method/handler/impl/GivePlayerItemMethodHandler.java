package org.apollo.backend.method.handler.impl;

import org.apollo.backend.method.handler.FrontendHandler;
import org.apollo.backend.method.impl.GivePlayerItemMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.json.JSONException;

/**
 * An {@link FrontendHandler} that handles {@link GivePlayerItemMethod} requests.
 * @author Steve
 */
public class GivePlayerItemMethodHandler extends FrontendHandler<GivePlayerItemMethod> {

	private boolean error = false;

	@Override
	public Object handle(GivePlayerItemMethod method) throws JSONException {
		String result = "";
		Player player = World.getWorld().getPlayer(method.getRequested().getArguments()[0]);
		if (player != null) {
			setPlayerItem(player, Integer.parseInt(method.getRequested().getArguments()[1]), Integer.parseInt(method.getRequested().getArguments()[2]),
					Boolean.parseBoolean(method.getRequested().getArguments()[3]));
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

	/**
	 * Give the specified player a item.
	 * @param player the player
	 * @param id the id
	 * @param amount the amount
	 * @param bank the bank
	 */
	private void setPlayerItem(Player player, int id, int amount, boolean bank) {
		if (bank) {
			player.getBank().add(id, amount);
		} else {
			player.getInventory().add(id, amount);
		}
	}
}
