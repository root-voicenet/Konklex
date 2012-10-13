package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the whois command.
 * @author Buroa
 */
public final class WhoisCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length == 1) {
			arguments[0] = arguments[0].toLowerCase();
			final boolean online = context.getServerChannelGroup().isPlayerOnline(arguments[0]);
			if (online) {
				final StringBuilder builder = new StringBuilder();
				builder.append("Username: " + arguments[0] + "\r\n");
				builder.append("Rights: " + context.getServerChannelGroup().getRights(arguments[0]) + "\r\n");
				builder.append("World: " + context.getServerChannelGroup().get(arguments[0]));
				channel.write(builder.toString() + "\r\n");
			} else {
				channel.write("-bash: " + command.getName().toLowerCase()+": player not found" + "\r\n");
			}
		}
	}

}
