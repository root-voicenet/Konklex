package org.apollo.game.command.impl;

import org.apollo.Server;
import org.apollo.ServerContext;
import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the send command.
 * @author Steve
 */
public final class SendCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length == 2) {
			String friend = arguments[0].toLowerCase();
			String message = arguments[1].replaceAll("_", " ");
			if (context.getServerChannelGroup().isPlayerOnline(friend)) {
				final int world = context.getServerChannelGroup().get(friend);
				Server.getContext().getServerChannelGroup().get(world)
						.write(new PrivateChatMethod(friend, "Server", 2, message));
			}
			else {
				channel.write("-bash: " + command.getName().toLowerCase() + ": player not found" + "\r\n");
			}
		}
	}

}