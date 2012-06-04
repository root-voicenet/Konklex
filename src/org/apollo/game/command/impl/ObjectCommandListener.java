package org.apollo.game.command.impl;

import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.game.model.GameObject;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.def.ObjectDefinition;

/**
 * An {@link CommandListener} for the {@code ::object}
 * @author Steve
 */
public final class ObjectCommandListener implements CommandListener {

    @Override
    public void execute(Player player, Command command) {
	if (command.getArguments().length == 3) {
	    final String cmd = command.getArguments()[0];
	    final int object = Integer.parseInt(command.getArguments()[1]);
	    final int rotation = Integer.parseInt(command.getArguments()[2]);
	    switch (cmd) {
	    case "add":
		GameObject obj = new GameObject(ObjectDefinition.forId(object), player.getPosition(), 10, rotation);
		World.getWorld().register(obj);
		break;
	    case "del":
		obj = new GameObject(ObjectDefinition.forId(object), player.getPosition(), 10, rotation);
		World.getWorld().unregister(obj);
		final GameObject newo = new GameObject(obj.getDefinition(), obj.getLocation(), 10, rotation, true);
		World.getWorld().register(newo);
		break;
	    }
	}
    }
}