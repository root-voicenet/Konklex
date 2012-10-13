package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the count command.
 * @author Steve
 */
public final class CountCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length == 1) {
			final int worldn = Integer.parseInt(arguments[0]);
			if (context.getServerChannelGroup().contains(worldn)) {
				final StringBuilder builder = new StringBuilder();
				final int players = context.getServerChannelGroup().getPlayers(worldn).size();
				builder.append(players + "\r\n");
				channel.write(builder.toString());
			} else {
				channel.write("-bash: " + command.getName().toLowerCase()+": world not connected" + "\r\n");
			}
		} else {
			if (context.getServerChannelGroup().size() > 0) {
				int total = 0;
				final StringBuilder builder = new StringBuilder();
				for (int i = 0; i < context.getServerChannelGroup().size(); i++) {
					final int players = context.getServerChannelGroup().getPlayers(i).size();
					builder.append("World " + (i + 1) + ": " + players + "\r\n");
					total += players;
				}
				builder.append("\r\n").append("Total: ").append(total).append(" players.");
				channel.write(builder.toString());
			} else {
				channel.write("-bash: " + command.getName().toLowerCase()+": no worlds connected" + "\r\n");
			}
		}
	}

}
