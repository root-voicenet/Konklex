package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.FriendsListEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.messaging.PlayerMessaging.Event;
import org.apollo.util.NameUtil;

/**
 * An {@link EventHandler} for the {@link FriendsListEvent}.
 * 
 * @author Steve
 */
public final class FriendsListEventHandler extends
EventHandler<FriendsListEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event
	 * .handler.EventHandlerContext, org.apollo.game.model.Player,
	 * org.apollo.game.event.Event)
	 */
	@Override
	public void handle(EventHandlerContext ctx, Player player,
			FriendsListEvent event) {
		try {
			final String friend = NameUtil.decodeBase37(event.getFriend());
			switch (event.getOpcode()) {
			case 188:
				player.getMessaging().add(friend, Event.FRIEND, false);
				player.getMessaging().refresh(friend);
				break;
			case 215:
				player.getMessaging().delete(friend, Event.FRIEND);
				break;
			case 133:
				player.getMessaging().add(friend, Event.IGNORE, false);
				break;
			case 74:
				player.getMessaging().delete(friend, Event.IGNORE);
				break;
			}
			ctx.breakHandlerChain();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}