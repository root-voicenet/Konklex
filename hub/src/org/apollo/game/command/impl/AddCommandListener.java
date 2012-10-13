package org.apollo.game.command.impl;

import java.net.InetSocketAddress;

import org.apollo.Server;
import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the add command.
 * @author Steve
 */
public final class AddCommandListener implements CommandListener {

	@Override
	public void execute(final Channel channel, final Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length >= 1) {
			String host = arguments[0];
			int port = Integer.valueOf(arguments.length == 2 ? Integer.valueOf(arguments[1]) : 43596);
			boolean persistant = Boolean.valueOf(arguments.length == 3 ? Boolean.parseBoolean(arguments[2]) : false);
			Server.getContext().connect(new InetSocketAddress(host, port), persistant);
		}
	}

}
