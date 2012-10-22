package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.ObjectActionEvent;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.obj.StaticObject;
import org.apollo.game.model.region.RegionManager;

/**
 * An {@link EventHandler} for the {@link ObjectActionEvent}'s.
 * @author Steve
 */
public final class ObjectVerificationHandler extends EventHandler<ObjectActionEvent> {

	@Override
	public void handle(EventHandlerContext ctx, Player player, ObjectActionEvent event) {
		boolean found = false;
		final RegionManager regionManager = World.getWorld().getRegionManager();
		for (GameObject object : regionManager.getLocalObjects(player)) {
			if (object.getDefinition().getId() == event.getId()) {
				if (object.getLocation().equals(event.getPosition())) {
					found = true;
					break;
				}
			}
		}
		if (!found) {
			for (StaticObject object : regionManager.getLocalStaticObjects(player)) {
				if (object.getId() == event.getId()) {
					if (object.getPosition().equals(event.getPosition())) {
						found = true;
						break;
					}
				}
			}
		}
		if (!found) {
			ctx.breakHandlerChain();
		}
	}
}
