package org.apollo.game.command.impl;

import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.game.model.inter.store.Shop;

/**
 * Implements a {@code ::shop} command that opens a shop.
 * @author Steve
 */
public final class ShopCommandListener implements CommandListener {

	@Override
	public void execute(Player player, Command command) {
		if (command.getArguments().length == 1) {
			int shop = Integer.parseInt(command.getArguments()[0]);
			World.getWorld().getStores().openShop(player, shop);
		} else
			if (command.getArguments().length == 2) {
				int shop = Integer.parseInt(command.getArguments()[0]);
				String name = command.getArguments()[1];
				World.getWorld().getStores().addShop(shop, new Shop(name));
			}
	}
}
