package org.apollo.game.model.inter.pouch;

import org.apollo.game.model.Player;
import org.apollo.util.TextUtil;

/**
 * Handles money pouch actions.
 * @author Solid Snake
 */
public class MoneyPouch {

	/**
	 * The integer that stores the amount of money a player has in their pouch.
	 */
	private int pouch;

	/**
	 * The player that owns this class.
	 */
	private final Player player;

	/**
	 * Create a new money pouch for the player.
	 * @param player The player to create the money pouch for.
	 */
	public MoneyPouch(Player player) {
		this.player = player;
	}

	/**
	 * Adds the *amount* of money to player's money pouch.
	 * @param amount The amount of money inserted.
	 * @param player The player
	 * @param delete
	 */
	public void add(int amount, boolean delete) {
		boolean max = false;
		if (getTotal() == Integer.MAX_VALUE) {
			player.sendMessage("Your money pouch is full.");
			return;
		}
		if (player.getInventory().getItemCount(995) > Integer.MAX_VALUE - getTotal()) {
			amount = Integer.MAX_VALUE - getTotal();
			max = true;
		}
		String amt = Integer.toString(amount);
		if (amount > 1) {
			player.sendMessage(TextUtil.insertCommas(amt) + " coins have been added to your money pouch.");
		} else {
			player.sendMessage("One coin has been added to your money pouch.");
		}
		pouch += amount;
		if (delete) {
			player.getInventory().remove(995, amount);
		}
		if (max) {
			player.getInventory().add(995, getTotal() - amount);
			max = false;
		}
		refresh();
	}

	/**
	 * Buying item from a shop.
	 * @param cost The cost of the item.
	 * @param amount cost * amount
	 * @return The amount of money left to take from.
	 */
	public int buy(int cost, int amount) {
		if (pouch <= 0) {
			return cost;
		} else {
			int removed = 0, remaining = 0;
			if (pouch - cost * amount < 0) {
				removed = pouch;
				remaining = cost * amount - pouch;
				pouch = 0;
			} else {
				removed = cost * amount;
				pouch -= cost * amount;
			}
			String amt = Integer.toString(removed);
			player.sendMessage((cost > 1 ? "One coin " : TextUtil.insertCommas(amt) + " coins ") + "have been deleted from your money pouch.");
			refresh();
			return remaining;
		}
	}

	/**
	 * Checks the total amount of money a player is storing in their pouch.
	 * @return The total amount of money a player is storing in thier pouch.
	 */
	public int getTotal() {
		return pouch;
	}

	/**
	 * Refreshes the money pouch.
	 * @param player The player.
	 */
	private void refresh() {
		if (player.getMoneyPouch().getTotal() > 99999 && player.getMoneyPouch().getTotal() <= 999999) {
			player.sendMessage("" + player.getMoneyPouch().getTotal() / 1000 + "K");
		} else
			if (player.getMoneyPouch().getTotal() > 999999 && player.getMoneyPouch().getTotal() <= 2147483647) {
				player.sendMessage("" + player.getMoneyPouch().getTotal() / 1000000 + "M");
			} else {
				player.sendMessage("" + player.getMoneyPouch().getTotal() + "");
			}
		player.sendMessage("" + player.getMoneyPouch().getTotal() + "");
	}

	/**
	 * Removes the *amount* of money from player's money pouch.
	 * @param amount The amount of money removed.
	 */
	public void remove(int amount) {
		if (player.getInventory().getItemCount(995) == Integer.MAX_VALUE) {
			return;
		}
		if (amount <= 0 || getTotal() == 0) {
			return;
		}
		if (getTotal() < amount) {
			amount = getTotal();
		}
		if (player.getInventory().getItemCount(995) > Integer.MAX_VALUE - amount) {
			amount = Integer.MAX_VALUE - player.getInventory().getItemCount(995);
		}
		if (amount > -1) {
			if (amount > getTotal()) {
				amount = getTotal();
			}
			String amt = Integer.toString(amount);
			if (amount > 1) {
				player.sendMessage(TextUtil.insertCommas(amt) + " coins have been removed from your money pouch.");
			} else {
				player.sendMessage("One coin has been removed from your money pouch.");
			}
			pouch -= amount;
			player.getInventory().remove(995, amount);
			refresh();
		}
	}

	/**
	 * Set the total amount of money a player has in their pouch.
	 * @param amount The total amount of money a player has in their pouch.
	 */
	public void setTotal(int amount) {
		pouch = amount;
	}
}
