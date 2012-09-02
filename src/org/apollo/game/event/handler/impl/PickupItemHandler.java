package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PickupItemEvent;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;

/**
 * An {@link EventHandler} for the group item instance.
 * @author Steve
 */
public final class PickupItemHandler extends EventHandler<PickupItemEvent> {

	/*
	 * (non-Javadoc)
	 * @see
	 * org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event
	 * .handler.EventHandlerContext, org.apollo.game.model.Player,
	 * org.apollo.game.event.Event)
	 */
	@Override
	public void handle(EventHandlerContext ctx, Player player, PickupItemEvent event) {
		final Position position = new Position(event.getX(), event.getY(), player.getPosition().getHeight());
		final GroundItem item = World.getWorld().getRegionManager().getGroundItem(player, position, event.getItemId());
		if (item != null) {
			if (item.getPosition().isWithinDistance(player.getPosition(), 1)) {
				if (player.getInventory().add(item.getItem()) == null)
					World.getWorld().unregister(item);
			} else
				ctx.breakHandlerChain();
		} else
			ctx.breakHandlerChain();
	}
}