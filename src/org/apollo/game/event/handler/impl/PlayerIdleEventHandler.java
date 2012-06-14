package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PlayerIdleEvent;
import org.apollo.game.model.Player;

/**
 * An {@link EventHandler} for the {@link PlayerIdleEvent}
 * @author Steve
 */
public final class PlayerIdleEventHandler extends EventHandler<PlayerIdleEvent> {

    @Override
    public void handle(EventHandlerContext ctx, Player player, PlayerIdleEvent event) {
	if (!player.isMembers()
		|| player.getPrivilegeLevel().toInteger() <= Player.PrivilegeLevel.MODERATOR.toInteger()) {
	    player.logout();
	}
    }
}