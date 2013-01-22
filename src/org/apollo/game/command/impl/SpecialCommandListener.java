package org.apollo.game.command.impl;

import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.game.model.Player;

/**
 * An {@link CommandListener} for the spec command.
 * @author Steve
 */
public final class SpecialCommandListener implements CommandListener {

	@Override
	public void execute(Player player, Command command) {
		player.getMeleeSet().setUsingSpecial(true);
		player.getMeleeSet().setSpecial(100);

		// TODO Implement this into the special bar
	}

}
