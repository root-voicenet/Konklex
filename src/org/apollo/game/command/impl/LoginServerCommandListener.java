package org.apollo.game.command.impl;

import org.apollo.game.command.Command;
import org.apollo.game.command.PrivilegedCommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;

/**
 * An {@link PrivilegedCommandListener} for the login server commands.
 * @author Steve
 */
public final class LoginServerCommandListener extends PrivilegedCommandListener {

	/**
	 * Creates the login server command listener.
	 */
	public LoginServerCommandListener() {
		super(PrivilegeLevel.DEVELOPER);
	}

	@Override
	public void executePrivileged(Player player, Command command) {
	}

}
