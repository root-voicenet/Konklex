package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the help command.
 * @author Steve
 */
public final class HelpCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		final StringBuilder builder = new StringBuilder();
		if (arguments.length == 0) {
			builder.append(
					"Konklex bash, version 3.2-release (" + System.getProperties().getProperty("java.runtime.version")
							+ ")").append("\r\n");
			builder.append("Type `help name' to find out more about the function `name'.").append("\r\n");
			builder.append("If a user has a space in their name, replace it with a _").append("\r\n").append("\r\n");
			builder.append("Commands:").append("\r\n");
			builder.append("    connect|c|add host [port]|<43596> [persistant]|<false>").append("\r\n");
			builder.append("    disconnect|d|remove [world]|<all>").append("\r\n");
			builder.append("    announce|ann [message] [world]|<all>").append("\r\n");
			builder.append("    message|msg [player] [messsage]").append("\r\n");
			builder.append("    command|cmd [args ...] [world]|<all>").append("\r\n");
			builder.append("    restart [time]|<0> [world]|<all>").append("\r\n");
			builder.append("    update [time]|<0> [world]|<all>").append("\r\n");
			builder.append("    status|s [world]|<all>").append("\r\n");
			builder.append("    uptime [world]|<all>").append("\r\n");
			builder.append("    count [world]|<all>").append("\r\n");
			builder.append("    whois [player]").append("\r\n");
			builder.append("    find [rights]").append("\r\n");
			builder.append("    help|? [topic]<this>").append("\r\n");
			builder.append("    exit").append("\r\n");
		}
		else if (arguments.length == 1) {
			final String cmd = arguments[0];
			switch (cmd) {
			case "count":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[world]|<all>").append("\r\n");
				builder.append("    Displays the current player count of a world or worlds.").append("\r\n")
						.append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      world         The world to get the player count from.").append("\r\n")
						.append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the world cannot be found.").append("\r\n");
				break;
			case "restart":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[time]<0> [world]|<all>")
						.append("\r\n");
				builder.append("    Closes the world and executes a new java application.").append("\r\n")
						.append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      time         The time (in 600ms ticks) to restart.").append("\r\n");
				builder.append("      world        The world to restart.").append("\r\n").append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the world cannot be found.").append("\r\n");
				break;
			case "update":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[time]<0> [world]|<all>")
						.append("\r\n");
				builder.append("    Restarts a world and displays the time on a player's screen.").append("\r\n")
						.append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      time         The time (in 600ms ticks) to update.").append("\r\n");
				builder.append("      world        The world to update.").append("\r\n").append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the world cannot be found.").append("\r\n");
				break;
			case "status":
			case "s":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[world]|<all>").append("\r\n");
				builder.append("    Displays useful information about a world or worlds.").append("\r\n")
						.append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      world        The world to get information about.").append("\r\n").append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the world cannot be found.").append("\r\n");
				break;
			case "uptime":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[world]|<all>").append("\r\n");
				builder.append("    Grabs the about of time a world or worlds have been online.").append("\r\n")
						.append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      world        The world to get the uptime from.").append("\r\n").append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the world cannot be found.").append("\r\n");
				break;
			case "whois":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[player]").append("\r\n");
				builder.append("    Grabs useful information about a remote player.").append("\r\n").append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      player        The player to grab information about.").append("\r\n")
						.append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the player cannot be found.").append("\r\n");
				break;
			case "find":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[rights]").append("\r\n");
				builder.append("    Finds any users that have the specified rights in their information file.")
						.append("\r\n").append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      rights        The rights to look for.").append("\r\n").append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns the names and the count of the players found.").append("\r\n");
				break;
			case "command":
			case "cmd":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[args ...] [world]|<all>")
						.append("\r\n");
				builder.append("    Executes a world command as a owner account.").append("\r\n").append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      args         The command line to be executed.").append("\r\n");
				builder.append("      world        The world for the command to be executed on.").append("\r\n")
						.append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the world cannot be found.").append("\r\n");
				break;
			case "message":
			case "msg":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[player] [message]").append("\r\n");
				builder.append("    Announces a message to a player.").append("\r\n").append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      player         The player to be receiving the message.").append("\r\n");
				builder.append("      message        The message to be sent to the player.").append("\r\n")
						.append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt.").append("\r\n");
				break;
			case "announce":
			case "ann":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[message] [world]|<all>")
						.append("\r\n");
				builder.append("    Announces a message to players of a world(s).").append("\r\n").append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      message        The message to be displayed to the players.").append("\r\n");
				builder.append("      world          The world to be broadcasted too.").append("\r\n").append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the world cannot be found.").append("\r\n");
				break;
			case "connect":
			case "c":
			case "add":
				builder.append(cmd).append(": ").append(cmd).append(" ")
						.append("host [port]|<43596> [persistant]|<false>").append("\r\n");
				builder.append("    Connects the login server to the specified host.").append("\r\n").append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      host        The host name or ip address to connect too.").append("\r\n");
				builder.append("      port        The port of the login server that exchanges data.").append("\r\n");
				builder.append("      persistant  The persistant (auto-try) connection field.").append("\r\n")
						.append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt.").append("\r\n");
				break;
			case "disconnect":
			case "d":
			case "remove":
				builder.append(cmd).append(": ").append(cmd).append(" ").append("[world]|<all>").append("\r\n");
				builder.append("    Breaks the connection between a world and the login server.").append("\r\n")
						.append("\r\n");
				builder.append("    Display the ARGs on the standard output followed by a newline.").append("\r\n")
						.append("\r\n");
				builder.append("    Options:").append("\r\n");
				builder.append("      world        The world to disconnect from.").append("\r\n").append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Returns a new prompt unless the world cannot be found.").append("\r\n");
				break;
			case "exit":
				builder.append(cmd).append(": ").append(cmd).append("\r\n");
				builder.append("    Disconnects this session.").append("\r\n").append("\r\n");
				builder.append("    Exit Status:").append("\r\n");
				builder.append("    Disconnects this session.").append("\r\n");
				break;
			default:
				builder.append("-bash: help: no help topics match `" + cmd + "'.").append("\r\n");
				break;
			}
		}
		channel.write(builder.toString());
	}

}
