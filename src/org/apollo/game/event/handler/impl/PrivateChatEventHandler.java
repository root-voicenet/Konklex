package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PrivateChatEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;

/**
 * An {@link EventHandler} for the {@link PrivateChatEvent}.
 * @author Steve
 */
public final class PrivateChatEventHandler extends EventHandler<PrivateChatEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event.handler.EventHandlerContext, org.apollo.game.model.Player, org.apollo.game.event.Event)
	 */
	@Override
	public void handle(EventHandlerContext ctx, Player player, PrivateChatEvent event) {
		World.getWorld().getMessaging().sendPrivateMessage(player, event);
		ctx.breakHandlerChain();
	}
}