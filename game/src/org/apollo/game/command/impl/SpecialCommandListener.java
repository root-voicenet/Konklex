package org.apollo.game.command.impl;

import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.game.model.Player;

public final class SpecialCommandListener implements CommandListener {

	@Override
	public void execute(Player player, Command command) {
		player.getMeleeSet().setUsingSpecial(true);
		player.getMeleeSet().setSpecial(100);
	}

}
