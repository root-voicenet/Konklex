package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.api.method.Method;
import org.apollo.api.method.impl.UpdateMethod;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the update command.
 * @author Steve
 */
public final class UpdateCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		final int time = arguments.length == 2 ? Integer.parseInt(arguments[1]) : 0;
		final int world = arguments.length == 3 ? Integer.parseInt(arguments[2]) : -1;
		final Method method = new UpdateMethod(time);
		if (world != -1)
			if (context.getServerChannelGroup().contains(world))
				context.getServerChannelGroup().get(world).write(method);
			else channel.write("-bash: " + command.getName().toLowerCase()+": world not connected" + "\r\n");
		else context.getServerChannelGroup().write(method);
	}

}
