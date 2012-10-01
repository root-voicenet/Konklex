package org.apollo.api.method.handler.impl;

import org.apollo.api.ProxyApiSession;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.PlayerCommandMethod;
import org.apollo.game.command.Command;
import org.apollo.game.model.World;

/**
 * An {@link MethodHandler} for the {@link PlayerCommandMethod}
 * @author Steve
 */
public final class PlayerCommandMethodHandler extends MethodHandler<PlayerCommandMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, PlayerCommandMethod method) {
		final World world = World.getWorld();
		final String player = method.getPlayer();
		final Command command = createCommand(method.getCommand());
		if (world.isPlayerOnline(player)) {
			world.getCommandDispatcher().dispatch(world.getPlayer(player), command);
		}
	}

	/**
	 * Creates the command.
	 * @param command The command to parse.
	 * @return The parsed {@link Command}.
	 */
	private Command createCommand(String command) {
		final String str = command;
		final String[] components = str.split(" ");
		final String name = components[0];
		final String[] arguments = new String[components.length - 1];
		System.arraycopy(components, 1, arguments, 0, arguments.length);
		return new Command(name, arguments);
	}

}
