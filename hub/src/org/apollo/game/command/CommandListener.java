package org.apollo.game.command;

import org.apollo.ServerContext;
import org.jboss.netty.channel.Channel;

/**
 * An interface which should be implemented by classes to listen to {@link Command}s.
 * @author Graham
 */
public interface CommandListener {

	/**
	 * Executes the action for this command.
	 * @param channel The channel.
	 * @param command The command.
	 * @param context The context.
	 */
	public void execute(Channel channel, Command command, ServerContext context);
}
