package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.ObjectLoadEvent;
import org.apollo.game.model.Player;

/**
 * An {@link EventHandler} for the {@link ObjectLoadEvent}.
 * @author Steve
 */
public final class ObjectLoadEventHandler extends EventHandler<ObjectLoadEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event.handler.EventHandlerContext, org.apollo.game.model.Player, org.apollo.game.event.Event)
	 */
	@Override
	public void handle(EventHandlerContext ctx, Player player, ObjectLoadEvent event) {
		player.getObjectSet().process();
		ctx.breakHandlerChain();
	}
}