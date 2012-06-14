package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.NpcOptionEvent;
import org.apollo.game.model.Player;

/**
 * An {@link EventHandler} for the {@link NpcOptionEvent}'s
 * @author Steve
 */
public class NpcVerificationHandler extends EventHandler<NpcOptionEvent> {

    @Override
    public void handle(EventHandlerContext ctx, Player player, NpcOptionEvent event) {
	if (event.getSlot() < 0 || event.getNpc() == null) {
	    ctx.breakHandlerChain();
	}
    }
}
