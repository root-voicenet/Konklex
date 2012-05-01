package org.apollo.game.event.handler.impl;

import org.apollo.game.action.impl.PlayerFollowAction;
import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PlayerFollowEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;

/**
 * An {@link EventHandler} for when a player follows another player.
 * @author Steve
 */
public final class PlayerFollowEventHandler extends EventHandler<PlayerFollowEvent> {

	@Override
	public void handle(EventHandlerContext ctx, Player player, PlayerFollowEvent event) {
		if (player.isDead() || !player.isActive()) {
			ctx.breakHandlerChain();
		} else {
			Player other = World.getWorld().getPlayerRepository().forIndex(event.getPlayerId());
			player.startAction(new PlayerFollowAction(player, other));
		}
	}
}