package org.apollo.game.command;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.ServerContext;
import org.apollo.game.command.impl.AddCommandListener;
import org.apollo.game.command.impl.AnnounceCommandListener;
import org.apollo.game.command.impl.CommandCommandListener;
import org.apollo.game.command.impl.CountCommandListener;
import org.apollo.game.command.impl.DisconnectCommandListener;
import org.apollo.game.command.impl.FindCommandListener;
import org.apollo.game.command.impl.ExitCommandListener;
import org.apollo.game.command.impl.HelpCommandListener;
import org.apollo.game.command.impl.RestartCommandListener;
import org.apollo.game.command.impl.SendCommandListener;
import org.apollo.game.command.impl.StatusCommandListener;
import org.apollo.game.command.impl.UpdateCommandListener;
import org.apollo.game.command.impl.UptimeCommandListener;
import org.apollo.game.command.impl.WhoisCommandListener;
import org.jboss.netty.channel.Channel;

/**
 * A class which dispatches {@link Command}s to {@link CommandListener}s.
 * @author Graham
 */
public final class CommandDispatcher {

	/**
	 * The instance.
	 */
	private final static CommandDispatcher instance = new CommandDispatcher();

	/**
	 * A map of event listeners.
	 */
	private final Map<String, CommandListener> listeners = new HashMap<String, CommandListener>();

	/**
	 * Creates the command dispatcher.
	 */
	public CommandDispatcher() {
		register(new AddCommandListener(), "add", "c", "connect");
		register(new DisconnectCommandListener(), "remove", "d", "disconnect");
		register(new CommandCommandListener(), "cmd", "command");
		register(new StatusCommandListener(), "s", "status");
		register(new SendCommandListener(), "msg", "message");
		register(new WhoisCommandListener(), "whois");
		register(new UptimeCommandListener(), "uptime");
		register(new CountCommandListener(), "count");
		register(new AnnounceCommandListener(), "ann", "announce");
		register(new RestartCommandListener(), "restart");
		register(new UpdateCommandListener(), "update");
		register(new FindCommandListener(), "find");
		register(new HelpCommandListener(), "help", "?");
		register(new ExitCommandListener(), "exit");
	}

	/**
	 * Dispatches a command to the appropriate listener.
	 * @param player The player.
	 * @param command The command.
	 * @param context The server context.
	 */
	public void dispatch(Channel channel, Command command, ServerContext context) {
		final CommandListener listener = listeners.get(command.getName().toLowerCase());
		if (listener != null)
			try {
				listener.execute(channel, command, context);
			}
			catch (Exception e) {
				Logger.getGlobal().log(Level.WARNING, "Error executing command", e.getCause());
			}
		else
			channel.write("-bash: " + command.getName().toLowerCase() + ": command not found" + "\r\n");
	}

	/**
	 * Registers a listener with the.
	 * @param listener The listener.
	 * @param command The command's.
	 */
	public void register(CommandListener listener, String... command) {
		for (String cmd : command)
			listeners.put(cmd.toLowerCase(), listener);
	}

	/**
	 * Gets the instance.
	 * @return The instance.
	 */
	public final static CommandDispatcher getInstance() {
		return instance;
	}
}
