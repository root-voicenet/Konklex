package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the uptime command.
 * @author Steve
 */
public final class UptimeCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length == 1) {
			final int worldn = Integer.parseInt(arguments[0]);
			if (context.getServerChannelGroup().contains(worldn)) {
				final StringBuilder builder = new StringBuilder();
				final int time = context.getServerChannelGroup().getWorld(worldn).getTime();
				final String readableTime = getElapsedTimeHoursMinutesSecondsString(time * 15000);
				builder.append(readableTime + "\r\n");
				channel.write(builder.toString());
			} else {
				channel.write("-bash: " + command.getName().toLowerCase()+": world not connected" + "\r\n");
			}
		} else {
			if (context.getServerChannelGroup().size() > 0) {
				final StringBuilder builder = new StringBuilder();
				for (int i = 0; i < context.getServerChannelGroup().size(); i++) {
					final int time = context.getServerChannelGroup().getWorld(i + 1).getTime();
					final String readableTime = getElapsedTimeHoursMinutesSecondsString(time * 15000);
					builder.append("World " + (i + 1) + ": " + readableTime + "\r\n");
				}
				channel.write(builder.toString());
			} else {
				channel.write("-bash: " + command.getName().toLowerCase()+": no worlds connected" + "\r\n");
			}
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
