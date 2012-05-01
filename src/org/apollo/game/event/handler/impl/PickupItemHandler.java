package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PickupItemEvent;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;

/**
 * An {@link EventHandler} for the group item instance.
 * @author Steve
 */
public final class PickupItemHandler extends EventHandler<PickupItemEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event.handler.EventHandlerContext, org.apollo.game.model.Player, org.apollo.game.event.Event)
	 */
	@Override
	public void handle(EventHandlerContext ctx, Player player, PickupItemEvent event) {
		Position position = new Position(event.getX(), event.getY(), player.getPosition().getHeight());
		GroundItem.getInstance().pickup(player, position, event.getItemId());
	}
}