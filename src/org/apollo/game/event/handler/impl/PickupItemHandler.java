package org.apollo.game.event.handler.impl;

import java.util.Collection;

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
	final GroundItem item = pickup(player.getName(), event.getItemId(), position);
	if (item != null) {
	    if (item.getPosition().isWithinDistance(player.getPosition(), 1)) {
		World.getWorld().unregister(item);
		player.getInventory().add(item.getItem());
	    } else {
		ctx.breakHandlerChain();
	    }
	} else {
	    ctx.breakHandlerChain();
	}
    }

    /**
     * Picks up a ground item.
     * @param controller The name of the controller.
     * @param item The item to pickup.
     * @param position The position of the item.
     * @return The ground item to pickup, or null if not able to.
     */
    private GroundItem pickup(String controller, int item, Position position) {
	final Collection<GroundItem> collection = World.getWorld().getRegionManager().getRegionByLocation(position)
		.getGroundItems();
	for (final GroundItem groundItem : collection) {
	    if (groundItem.getPosition().equals(position)) {
		if (groundItem.getItem().getId() == item) {
		    if (groundItem.getControllerName().equalsIgnoreCase(controller) || groundItem.getPulses() == 0) {
			return groundItem;
		    }
		}
	    }
	}
	return null;
    }
}