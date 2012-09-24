package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.MagicOnPlayerEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.World;

/**
 * An {@link EventHandler} for the {@link MagicOnPlayerEvent}
 * @author Steve
 */
public final class MagicOnPlayerVerificationHandler extends EventHandler<MagicOnPlayerEvent> {

	@Override
	public void handle(EventHandlerContext ctx, Player player, MagicOnPlayerEvent event) {
		final int id = event.getPlayerId();
		final Player victim = World.getWorld().getPlayerRepository().forIndex(id);
		if (id < 0)
			ctx.breakHandlerChain();
		else if (World.getWorld().getPlayerRepository().forIndex(id) == null)
			ctx.breakHandlerChain();
		else {
			if (victim.getPrivilegeLevel().equals(PrivilegeLevel.DEVELOPER)) {
				player.sendMessage("Sorry, but Developers are protected from your attacks.");
				ctx.breakHandlerChain();
			}
		}
	}

}
