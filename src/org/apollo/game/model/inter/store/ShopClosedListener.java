package org.apollo.game.model.inter.store;

import org.apollo.game.model.Player;
import org.apollo.game.model.inter.InterfaceListener;

/**
 * An {@link InterfaceListener} for the shops.
 * @author Steve
 */
public final class ShopClosedListener implements InterfaceListener {

	/**
	 * The player.
	 */
	private final Player player;

	/**
	 * The shop.
	 */
	private final Shop shop;

	/**
	 * Create a shop listener for the player.
	 * @param player The player.
	 * @param shop The shop.
	 */
	public ShopClosedListener(Player player, Shop shop) {
		this.player = player;
		this.shop = shop;
		init();
	}

	/**
	 * Adds some shop things.
	 */
	private void init() {
		player.setShop(shop);
		shop.addViewer(player);
	}

	@Override
	public void interfaceClosed() {
		shop.removeViewer(player);
		player.setShop(null);
	}
}
