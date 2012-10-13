package org.apollo.game.model.messaging;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

import org.apollo.api.FrontendService;
import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.game.event.impl.PrivateChatLoadedEvent;
import org.apollo.game.event.impl.SendPrivateChatEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.util.NameUtil;
import org.apollo.util.TextUtil;

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
		for (int i = 0; i < worlds; i++)
			players.put(i, new ArrayList<String>());
	}

	/**
	 * Deregisters a player.
	 * @param player the player
	 */
	public void deregister(Object player) {
		if (player instanceof Player)
			events.add(((Player) player).getName().toLowerCase());
		else if (player instanceof String)
			events.add(((String) player).toLowerCase());
	}

	/**
	 * Deregisters a player from a world.
	 * @param player The player.
	 * @param world The player's world.
	 */
	public void deregister(String player, int world) {
		player = player.toLowerCase();
		if (players.get(world).contains(player))
			if (players.get(world).remove(player))
				sendStatus(player);
	}

	/**
	 * Send the events to outstream.
	 */
	public void dispatch() {
		final String login = events.poll();
		if (login != null)
			sendStatus(login);
	}

	/**
	 * Checks if the player is online.
	 * @param player The player to check.
	 * @return True if online, false if not.
	 */
	public boolean isPlayerOnline(String player) {
		player = player.toLowerCase();
		boolean online = World.getWorld().isPlayerOnline(player);
		for (int i = 0; i < worlds; i++)
			if (players.get(i).contains(player))
				online = true;
		return online;
	}

	/**
	 * Registers a player.
	 * @param player the player
	 */
	public void register(Object player) {
		if (player instanceof Player) {
			final Player user = (Player) player;
			user.send(new PrivateChatLoadedEvent(2));
			try {
				user.getMessaging().refresh();
			}
			catch (final Exception e) {
				// do nothing
			}
			events.add(user.getName().toLowerCase());
		}
		else if (player instanceof String)
			events.add(((String) player).toLowerCase());
	}

	/**
	 * Deregisters a player from another world.
	 * @param player The player.
	 * @param world The player's world.
	 */
	public void register(String player, int world) {
		player = player.toLowerCase();
		if (!players.get(world).contains(player))
			if (players.get(world).add(player))
				sendStatus(player);
	}

	/**
	 * Send a private message.
	 * @param sender The sender of the message.
	 * @param reciever The receiving player.
	 * @param message The message to send.
	 */
	public void sendPrivateMessage(Player sender, long reciever, byte[] message) {
		final Player friend = World.getWorld().getPlayer(NameUtil.decodeBase37(reciever));
		if (friend != null)
			friend.send(new SendPrivateChatEvent(sender.getEncodedName(), sender.getPrivilegeLevel().toInteger(),
					message, friend.getMessaging().getLastId()));
		else
			World.getWorld()
					.getContext()
					.getService(FrontendService.class)
					.sendAll(
							new PrivateChatMethod(reciever, sender.getEncodedName(), sender.getPrivilegeLevel()
									.toInteger(), message));
	}

	/**
	 * Send a private message.
	 * @param sender The sender of the message.
	 * @param reciever The receiving player.
	 * @param message The message to send.
	 */
	public void sendPrivateMessage(String sender, String reciever, String message) {
		final Player friend = World.getWorld().getPlayer(reciever);
		String uncompressed = message;
		uncompressed = TextUtil.filterInvalidCharacters(message);
		uncompressed = TextUtil.capitalize(message);
		final byte[] recompressed = new byte[uncompressed.length()];
		TextUtil.compress(uncompressed, recompressed);
		if (friend != null) {
			friend.send(new SendPrivateChatEvent(NameUtil.encodeBase37(sender), 0, recompressed, friend.getMessaging()
					.getLastId()));
		}
		else {
			World.getWorld().getContext().getService(FrontendService.class)
					.sendAll(new PrivateChatMethod(reciever, sender, 0, message));
		}
	}

	/**
	 * Gets the world.
	 * @param player The player.
	 * @return The world.
	 */
	public int getWorld(String player) {
		for (final Entry<Integer, ArrayList<String>> entry : players.entrySet())
			if (entry.getValue().contains(player))
				return entry.getKey();
		return -1;
	}

	/**
	 * Send the other players that a user is online.
	 * @param player the player
	 */
	private void sendStatus(String player) {
		player = player.toLowerCase();
		for (final Player all : World.getWorld().getPlayerRepository())
			try {
				all.getMessaging().refresh(player);
			}
			catch (final Exception e) {
				// do nothing
			}
	}
}
