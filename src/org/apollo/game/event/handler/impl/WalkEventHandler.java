package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.WalkEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.WalkingQueue;

/**
 * A handler for the {@link WalkEvent}.
 * @author Graham
 */
public final class WalkEventHandler extends EventHandler<WalkEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event.handler.EventHandlerContext, org.apollo.game.model.Player, org.apollo.game.event.Event)
	 */
	@Override
	public void handle(EventHandlerContext ctx, Player player, WalkEvent event) {
		WalkingQueue queue = player.getWalkingQueue();
		Position[] steps = event.getSteps();
		for (int i = 0; i < steps.length; i++) {
			Position step = steps[i];
			if (i == 0) {
				if (!queue.addFirstStep(step)) {
					return; /* ignore packet */
				}
			} else {
				queue.addStep(step);
			}
		}
		if (!player.getWalkingQueue().getRunning()) {
			queue.setRunning(event.isRunning());
		}
		if (queue.size() > 0) {
			player.stopAction();
			player.stopFacing();
			player.getInterfaceSet().close();
		}
	}
}
