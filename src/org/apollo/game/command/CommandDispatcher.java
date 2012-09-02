package org.apollo.game.command;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.command.impl.CreditsCommandListener;
import org.apollo.game.command.impl.MoveCommandListener;
import org.apollo.game.command.impl.ObjectCommandListener;
import org.apollo.game.command.impl.ShopCommandListener;
import org.apollo.game.model.Player;

/**
 * A class which dispatches {@link Command}s to {@link CommandListener}s.
 * @author Graham
 */
public final class CommandDispatcher {

	/**
	 * A map of event listeners.
	 */
	private final Map<String, CommandListener> listeners = new HashMap<String, CommandListener>();

	/**
	 * Creates the command dispatcher.
	 */
	public CommandDispatcher() {
		register("credits", new CreditsCommandListener());
		register("credits", new CreditsCommandListener());
		register("shop", new ShopCommandListener());
		register("object", new ObjectCommandListener());
		register("move", new MoveCommandListener());
	}

	/**
	 * Dispatches a command to the appropriate listener.
	 * @param player The player.
	 * @param command The command.
	 */
	public void dispatch(Player player, Command command) {
		final CommandListener listener = listeners.get(command.getName().toLowerCase());
		if (listener != null)
			listener.execute(player, command);
	}

	/**
	 * Registers a listener with the.
	 * @param command The command's name.
	 * @param listener The listener.
	 */
	public void register(String command, CommandListener listener) {
		listeners.put(command.toLowerCase(), listener);
	}
}
