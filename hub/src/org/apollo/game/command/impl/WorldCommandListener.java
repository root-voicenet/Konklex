package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.api.method.Method;
import org.apollo.api.method.impl.PlayerCommandMethod;
import org.apollo.api.method.impl.ReceivePlayerMethod;
import org.apollo.api.method.impl.UpdateMethod;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.util.NameUtil;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the world commands.
 * @author Steve
 */
public final class WorldCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length >= 2) {
			final int world = Integer.valueOf(arguments[0]);
			if (context.getServerChannelGroup().contains(world)) {
				switch(arguments[1]) {
				case "player":
					if (arguments.length >= 3) {
						switch(arguments[2]) {
						case "add":
						case "remove":
							if (arguments.length == 4) {
								final String player = arguments[3];
								final ReceivePlayerMethod method = new ReceivePlayerMethod(NameUtil.encodeBase37(player), 0, arguments[2].equalsIgnoreCase("add") ? 1 : 0, world);
								context.getServerChannelGroup().write(method, world, false);
							}
							break;
						case "cmd":
							if (arguments.length >= 4) {
								final String player = arguments[3];
								final String[] components = arguments;
								final String[] args = new String[components.length - 4];
								System.arraycopy(components, 4, args, 0, args.length);
								String returnc = "";
								for (String string : args) {
									returnc += string + " ";
								}
								final Method method = new PlayerCommandMethod(player, returnc);
								context.getServerChannelGroup().get(world).write(method);
							}
							break;
						}
					}
				case "update":
					if (arguments.length == 3) {
						final int time = Integer.valueOf(arguments[2]);
						context.getServerChannelGroup().get(world).write(new UpdateMethod(time));
					}
					break;
				case "disconnect":
					context.getServerChannelGroup().get(world).close();
					break;
				case "ls":
					final String returna = context.getServerChannelGroup().getPlayers(world).toString();
					channel.write(returna + "\r\n");
					break;
				case "count":
					final int players = context.getServerChannelGroup().size(world);
					channel.write(players + "\r\n");
					break;
				case "uptime":
					//final int time = context.getServerChannelGroup().getTime(world);
					//final String returnb = getElapsedTimeHoursMinutesSecondsString(time * 15000);
					//channel.write(returnb + "\r\n");
					break;
				default:
					channel.write("-bash: " + arguments[1]+": command not found" + "\r\n");
					break;
				}
			} else
				channel.write("-bash: " + command.getName().toLowerCase()+": world not connected" + "\r\n");
		}
	}
	
	/**
	 * Gets the elapsed time in hours, minutes, and seconds.
	 * @return The elapsed time in hours, minutes, and seconds.
	 */
	public String getElapsedTimeHoursMinutesSecondsString(long serverTime) {     
	    long elapsedTime = serverTime;
	    String format = String.format("%%0%dd", 2);
	    elapsedTime = elapsedTime / 1000;
	    String seconds = String.format(format, elapsedTime % 60);
	    String minutes = String.format(format, (elapsedTime % 3600) / 60);
	    String hours = String.format(format, elapsedTime / 3600);
	    String time =  hours + ":" + minutes + ":" + seconds;
	    return time;
	}

}
