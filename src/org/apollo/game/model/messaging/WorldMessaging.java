package org.apollo.game.model.messaging;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.apollo.game.event.impl.PrivateChatEvent;
import org.apollo.game.event.impl.PrivateChatLoadedEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.util.NameUtil;

/**
 * The global private chat system.
 * @author Steve
 */
public final class WorldMessaging {

	/**
	 * The events.
	 */
	private final Queue<String> events = new ArrayDeque<String>();

	/**
	 * The other world's players.
	 */
	private final Map<Integer, ArrayList<String>> players = new HashMap<Integer, ArrayList<String>>();

	/**
	 * The maximum worlds for private messaging.
	 */
	private final int worlds = 20;

	/**
	 * Creates empty nodes to put players in.
	 */
	public WorldMessaging() {
		for (int i = 0; i < worlds; i++) {
			players.put(i, new ArrayList<String>());
		}
	}

	/**
	 * Deregisters a player.
	 * @param player the player
	 */
	public void deregister(Object player) {
		if (player instanceof Player) {
			events.add(((Player) player).getName());
		} else
			if (player instanceof String) {
				events.add((String) player);
			}
	}

	/**
	 * Deregisters a player from a world.
	 * @param player The player.
	 * @param world The player's world.
	 */
	public void deregister(String player, int world) {
		player = player.toLowerCase();
		if (players.get(world).contains(player)) {
			if (players.get(world).remove(player)) {
				sendStatus(player);
			}
		}
	}

	/**
	 * Send the events to outstream.
	 */
	public void dispatch() {
		if (!events.isEmpty()) {
			for (String login : events) {
				sendStatus(login);
			}
			events.clear();
		}
	}

	/**
	 * Checks if the player is online.
	 * @param player The player to check.
	 * @return True if online, false if not.
	 */
	public boolean isPlayerOnline(String player) {
		player = player.toLowerCase();
		for (int i = 0; i < worlds; i++) {
			if (players.get(i).contains(player)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Registers a player.
	 * @param player the player
	 */
	public void register(Object player) {
		if (player instanceof Player) {
			Player user = (Player) player;
			try {
				user.getMessaging().refresh();
			} catch (Exception e) {
				// do nothing
			}
			user.send(new PrivateChatLoadedEvent(2));
			events.add(user.getName());
		} else
			if (player instanceof String) {
				events.add((String) player);
			}
	}

	/**
	 * Deregisters a player from another world.
	 * @param player The player.
	 * @param world The player's world.
	 */
	public void register(String player, int world) {
		player = player.toLowerCase();
		if (!players.get(world).contains(player)) {
			if (players.get(world).add(player)) {
				sendStatus(player);
			}
		}
	}

	/**
	 * Send a private message.
	 * @param sender the sender
	 * @param event the event
	 */
	public void sendPrivateMessage(Player sender, PrivateChatEvent event) {
		Player friend = World.getWorld().getPlayer(NameUtil.decodeBase37(event.getFriendLong()));
		if (friend != null) {
			event.setFriendRights(sender.getPrivilegeLevel().toInteger());
			event.setLastId(friend.getMessaging().getLastId());
			event.setFrom(sender);
			friend.send(event);
		} else {
			//World.getWorld().getLoginServer().sendPrivateMessage(sender, event);
		}
	}

	/**
	 * Send the other players that a user is online.
	 * @param player the player
	 */
	private void sendStatus(String player) {
		player = player.toLowerCase();
		for (Player all : World.getWorld().getPlayerRepository()) {
			try {
				all.getMessaging().refresh(player);
			} catch (Exception e) {
				// do nothing
			}
		}
	}
}
