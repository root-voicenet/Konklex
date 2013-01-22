package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.MagicEvent;
import org.apollo.game.event.impl.MagicOnPlayerEvent;
import org.apollo.game.model.Npc;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.World;

/**
 * An {@link EventHandler} for the {@link MagicOnPlayerEvent}
 * @author Steve
 */
public final class MagicOnPlayerVerificationHandler extends EventHandler<MagicEvent> {

	@Override
	public void handle(EventHandlerContext ctx, Player player, MagicEvent event) {
		final int id = event.getCharacterId();
		switch (event.getOption()) {
		case 0:
			final Player victim = World.getWorld().getPlayerRepository().forIndex(id);
			if (id < 0)
				ctx.breakHandlerChain();
			else if (victim == null)
				ctx.breakHandlerChain();
			else {
				if (victim.getPrivilegeLevel().equals(PrivilegeLevel.DEVELOPER)) {
					player.sendMessage("Sorry, but Developers are protected from your attacks.");
					ctx.breakHandlerChain();
				}
			}
			break;
		case 1:
			final Npc victim1 = World.getWorld().getNpcRepository().forIndex(id);
			if (id < 0)
				ctx.breakHandlerChain();
			else if (victim1 == null)
				ctx.breakHandlerChain();
			break;
		}
	}

}
