package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PlayerReportEvent;
import org.apollo.game.model.Player;

/**
 * An {@link EventHandler} for the {@link PlayerReportEvent}
 * @author Steve
 */
public final class PlayerReportEventHandler extends EventHandler<PlayerReportEvent> {

    @Override
    public void handle(EventHandlerContext ctx, Player player, PlayerReportEvent event) {
	player.sendMessage("Thank you, your report has been recieved.");
    }

}
