package org.apollo.util;

import org.apollo.game.command.Command;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.security.PlayerCredentials;

/**
 * A default utility.
 * @author Steve
 */
public final class DefaultUtil {

	/**
	 * Creates the command.
	 * @param command The command to parse.
	 * @return The parsed {@link Command}.
	 */
	public static Command createCommand(String command) {
		final String str = command;
		final String[] components = str.split(" ");
		final String name = components[0];
		final String[] arguments = new String[components.length - 1];
		System.arraycopy(components, 1, arguments, 0, arguments.length);
		return new Command(name, arguments);
	}

	/**
	 * Executes a command.
	 * @param command The command.
	 */
	public static void executeCommand(Command command) {
		final PlayerCredentials credentials = new PlayerCredentials("Server", "", 0, 0);
		final Player fakePlayer = new Player(credentials, new Position(0, 0));
		fakePlayer.setPrivilegeLevel(PrivilegeLevel.OWNER);
		World.getWorld().getCommandDispatcher().dispatch(fakePlayer, command);
	}

}
