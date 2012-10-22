package org.apollo.game.command.impl;

import java.io.File;

import org.apollo.Server;
import org.apollo.game.command.Command;
import org.apollo.game.command.PrivilegedCommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link PrivilegedCommandListener} for the restart command.
 * @author Steve
 */
public final class RestartCommandListener extends PrivilegedCommandListener {

	/**
	 * Restarts this application.
	 */
	public static final void restart() {
		try {
			final boolean isWindows = Server.isWindows();
			final String seperator = Server.getSeperator();
			final File file = new File(".").getCanonicalFile();
			final String cmd[] = isWindows ? new String[] { "java", "-cp bin;deps" + seperator + "*",
					"org.apollo.Server" }
			: new String[] { "sh", "-c", "Run_Game.sh" };
			Runtime.getRuntime().exec(cmd, null, file);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	/**
	 * Creates the restart command listener.
	 */
	public RestartCommandListener() {
		super(PrivilegeLevel.DEVELOPER);
	}

	@Override
	public void executePrivileged(Player player, Command command) {
		final String[] arguments = command.getArguments();
		if (arguments.length == 1) {
			World.getWorld().schedule(new ScheduledTask(Integer.parseInt(arguments[0]), false) {

				@Override
				public void execute() {
					restart();
				}

			});
		}
		else {
			restart();
		}
	}

}
