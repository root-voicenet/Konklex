package org.apollo.game.command.impl;

import java.util.Map.Entry;

import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} that searches for the find online.
 * @author Steve
 */
public final class FindCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length == 1) {
			final int rights = Integer.parseInt(arguments[0]);
			int count = 0;
			final StringBuilder builder = new StringBuilder();
			for(Entry<Integer, World> worlds : context.getServerChannelGroup().getWorlds().entrySet()) {
				for (Entry<String, Player> player : worlds.getValue().getPlayers().entrySet()) {
					if (player.getValue().getRights() == rights) {
						count++;
						final String user = player.getKey();
						builder.append("Username: ").append(user).append(", World: ").append(worlds.getKey()).append("\r\n");
					}
				}
			}
			builder.append("Found: ").append(count).append(" accounts.").append("\r\n");
			channel.write(builder.toString());
		}
	}

}
