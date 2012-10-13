package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.api.method.Method;
import org.apollo.api.method.impl.PlayerCommandMethod;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the announce command.
 * @author Steve
 */
public final class AnnounceCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length >= 1) {
			int world = -1;
			final Method method = new PlayerCommandMethod("Server", "yell" + " " + arguments[0].replaceAll("_", " "));
			if (arguments.length == 2) world = Integer.parseInt(arguments[1]);
			if (world != -1)
				if (context.getServerChannelGroup().contains(world))
					context.getServerChannelGroup().get(world).write(method);
				else channel.write("-bash: " + command.getName().toLowerCase()+": world not connected" + "\r\n");
			else context.getServerChannelGroup().write(method);
		}
	}

}
