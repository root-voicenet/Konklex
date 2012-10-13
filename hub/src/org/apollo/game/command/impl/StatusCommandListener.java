package org.apollo.game.command.impl;

import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.game.model.World;
import org.jboss.netty.channel.Channel;

/**
 * An {@link CommandListener} for the status command.
 * @author Steve
 */
public final class StatusCommandListener implements CommandListener {

	@Override
	public void execute(Channel channel, Command command, ServerContext context) {
		final String[] arguments = command.getArguments();
		if (arguments.length == 1) {
			final int worldn = Integer.parseInt(arguments[0]);
			if (context.getServerChannelGroup().contains(worldn)) {
				final StringBuilder builder = new StringBuilder();
				final World world = context.getServerChannelGroup().getWorld(worldn);
				builder.append("CPU: ").append(world.getCpu()).append("\r\n");
				builder.append("RAM: ").append(world.getRam()).append("\r\n");
				builder.append("Threads: ").append(world.getThreads()).append("\r\n");
				final int time = world.getTime();
				builder.append("Time: ").append(time).append("\r\n");
				builder.append("Real Time: ").append(getElapsedTimeHoursMinutesSecondsString(time * 15000)).append("\r\n");
				builder.append("Items: ").append(world.getItems()).append("\r\n");
				builder.append("Npcs: ").append(world.getNpcs()).append("\r\n");
				builder.append("Objects: ").append(world.getObjects()).append("\r\n");
				builder.append("Regions: ").append(world.getRegions()).append("\r\n");
				builder.append("Updating: ").append(world.getStatus()).append("\r\n");
				channel.write(builder.toString());
			} else {
				channel.write("-bash: " + command.getName().toLowerCase()+": world not connected" + "\r\n");
			}
		} else {
			if (context.getServerChannelGroup().size() > 0) {
				final StringBuilder builder = new StringBuilder();
				for (int i = 0; i < context.getServerChannelGroup().size(); i++) {
					final World world = context.getServerChannelGroup().getWorld(i + 1);
					builder.append("World: " + (i + 1)).append("\r\n");
					builder.append("  CPU: ").append(world.getCpu()).append("\r\n");
					builder.append("  RAM: ").append(world.getRam()).append("\r\n");
					builder.append("  Threads: ").append(world.getThreads()).append("\r\n");
					final int time = world.getTime();
					builder.append("  Time: ").append(time).append("\r\n");
					builder.append("  Real Time: ").append(getElapsedTimeHoursMinutesSecondsString(time * 15000)).append("\r\n");
					builder.append("  Items: ").append(world.getItems()).append("\r\n");
					builder.append("  Npcs: ").append(world.getNpcs()).append("\r\n");
					builder.append("  Objects: ").append(world.getObjects()).append("\r\n");
					builder.append("  Regions: ").append(world.getRegions()).append("\r\n");
					builder.append("  Updating: ").append(world.getStatus()).append("\r\n");
					builder.append("  Players: ").append(world.getPlayers().size()).append("\r\n");
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
