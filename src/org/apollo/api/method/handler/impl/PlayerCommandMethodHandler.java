package org.apollo.api.method.handler.impl;

import org.apollo.api.ProxyApiSession;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.PlayerCommandMethod;
import org.apollo.game.command.Command;
import org.apollo.game.model.World;
import org.apollo.util.DefaultUtil;

/**
 * An {@link MethodHandler} for the {@link PlayerCommandMethod}
 * @author Steve
 */
public final class PlayerCommandMethodHandler extends MethodHandler<PlayerCommandMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, PlayerCommandMethod method) {
		final World world = World.getWorld();
		final String player = method.getPlayer();
		final Command command = DefaultUtil.createCommand(method.getCommand());
		if (world.isPlayerOnline(player)) {
			world.getCommandDispatcher().dispatch(world.getPlayer(player), command);
		}
		else if (player.equalsIgnoreCase("Server")) {
			DefaultUtil.executeCommand(command);
		}
	}

}
