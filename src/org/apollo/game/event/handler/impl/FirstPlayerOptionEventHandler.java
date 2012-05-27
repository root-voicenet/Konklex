package org.apollo.game.event.handler.impl;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.FirstPlayerOptionEvent;
import org.apollo.game.event.impl.PlayerOptionEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.inter.trade.TradeUtilities;

/**
 * An {@link EventHandler} for the {@link FirstPlayerOptionEvent}
 * @author Steve
 */
public final class FirstPlayerOptionEventHandler extends EventHandler<PlayerOptionEvent> {

    /**
     * An {@link DistancedAction} that represents a player trade request.
     * @author Steve
     */
    private static final class PlayerTradeRequestAction extends DistancedAction<Player> {

	/**
	 * The acquaintance.
	 */
	private final Player acquaintance;

	/**
	 * Create a new player trade action.
	 * @param player The player.
	 * @param acquaintance The acquaintance.
	 */
	public PlayerTradeRequestAction(Player player, Player acquaintance) {
	    super(1, true, player, acquaintance.getPosition(), 1);
	    this.acquaintance = acquaintance;
	}

	@Override
	public void executeAction() {
	    TradeUtilities.sendTradeRequest(getCharacter(), acquaintance);
	    stop();
	}
    }

    /*
     * (non-Javadoc)
     * @see
     * org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event
     * .handler.EventHandlerContext, org.apollo.game.model.Player,
     * org.apollo.game.event.Event)
     */
    @Override
    public void handle(EventHandlerContext ctx, Player player, PlayerOptionEvent event) {
	if (event.getOption() == 1) {
	    final Player acquaintance = World.getWorld().getPlayerRepository().forIndex(event.getPlayerId());
	    if (player != null) {
		player.turnTo(acquaintance.getPosition());
		player.startAction(new PlayerTradeRequestAction(player, acquaintance));
		ctx.breakHandlerChain();
	    }
	}
    }
}
