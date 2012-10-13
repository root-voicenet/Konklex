package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the disconnect command.
 * @author Steve
 */
public final class DisconnectCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length == 1) {
			final int world = Integer.parseInt(arguments[0]);
			if (context.getServerChannelGroup().contains(world))
				context.getServerChannelGroup().get(world).close();
			else
				channel.write("-bash: " + command.getName().toLowerCase() + ": world not connected" + "\r\n");
		}
		else
			context.getServerChannelGroup().close();
	}

}
