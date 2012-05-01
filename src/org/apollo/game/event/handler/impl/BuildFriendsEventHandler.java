package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.FriendEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.messaging.PlayerMessaging.Event;

/**
 * An {@link EventHandler} for the {@link FriendEvent}.
 * @author Steve
 */
public class BuildFriendsEventHandler extends EventHandler<FriendEvent> {

	/*
	 * (non-Javadoc)
	 * @see org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event.handler.EventHandlerContext, org.apollo.game.model.Player, org.apollo.game.event.Event)
	 */
	@Override
	public void handle(EventHandlerContext ctx, Player player, FriendEvent event) {
		try {
			switch (event.getOpcode()) {
				case 188:
					player.getMessaging().add(event.getFriend(), Event.FRIEND, false);
					player.getMessaging().refresh(event.getFriend());
					break;
				case 215:
					player.getMessaging().delete(event.getFriend(), Event.FRIEND);
					break;
				case 133:
					player.getMessaging().add(event.getFriend(), Event.IGNORE, false);
					break;
				case 74:
					player.getMessaging().delete(event.getFriend(), Event.IGNORE);
					break;
			}
			ctx.breakHandlerChain();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}