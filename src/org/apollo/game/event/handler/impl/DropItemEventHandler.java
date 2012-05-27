package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.ItemOptionEvent;
import org.apollo.game.model.GroundItem;
import org.apollo.game.model.Player;

/**
 * A handler for the {@link DropItemEvent}.
 * @author Steve
 */
public final class DropItemEventHandler extends EventHandler<ItemOptionEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event.handler.EventHandlerContext,
	 * org.apollo.game.model.Player, org.apollo.game.event.Event)
	 */
	@Override
	public void handle(EventHandlerContext ctx, Player player, ItemOptionEvent event) {
		if (event.getOption() == 5) {
			int slot = event.getSlot();
			GroundItem.getInstance().create(player, event.getId(), player.getInventory().get(slot).getAmount(),
					player.getPosition());
			player.getInventory().reset(slot);
		}
	}
}