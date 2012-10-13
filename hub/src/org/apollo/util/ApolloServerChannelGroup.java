package org.apollo.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.DefaultChannelGroup;

/**
 * An {@link DefaultChannelGroup} that extends methods.
 * @author Steve
 */
public final class ApolloServerChannelGroup extends DefaultChannelGroup {

	/**
	 * The channels.
	 */
	private final List<Channel> channels = new ArrayList<Channel>();

	/**
	 * The worlds.
	 */
	private final Map<Integer, World> worlds = new HashMap<Integer, World>();

	@Override
	public boolean add(Channel e) {
		worlds.put(channels.size() + 1, new World());
		return channels.add(e) && super.add(e);
	}

	/**
	 * Writes to other worlds.
	 * @param object The object to write.
	 * @param world The world.
	 * @param includeMe The include me flag.
	 */
	public void write(Object object, int world, boolean includeMe) {
		if (includeMe)
			write(object);
		else
			for (int i = 0; i < channels.size(); i++) {
				if (i + 1 != world) {
					channels.get(i).write(object);
				}
			}
	}

	/**
	 * Checks if a player is online.
	 * @param index The index.
	 * @param player The player.
	 * @return True if online, false if otherwise.
	 */
	public boolean isPlayerOnline(int index, String player) {
		return worlds.containsKey(index) && worlds.get(index).isPlayerOnline(player);
	}

	/**
	 * Checks if a player is online.
	 * @param index The index.
	 * @param player The player.
	 * @return True if online, false if otherwise.
	 */
	public boolean isPlayerOnline(String player) {
		boolean contains = false;
		for (Entry<Integer, World> world : worlds.entrySet()) {
			if (world.getValue().isPlayerOnline(player))
				contains = true;
		}
		return contains;
	}

	@Override
	public boolean remove(Object o) {
		return channels.remove(o) && super.remove(o);
	}

	/**
	 * Checks if the group contains the world.
	 * @param index The world index.
	 * @return True if contains, false if otherwise.
	 */
	public boolean contains(int index) {
		return worlds.containsKey(index);
	}

	/**
	 * Gets the channel.
	 * @param index The world index.
	 * @return The channel.
	 */
	public Channel get(int index) {
		if (worlds.containsKey(index))
			return channels.get(index);
		return null;
	}

	/**
	 * Gets the player.
	 * @param player The player.
	 * @return The world number.
	 */
	public int get(String player) {
		for (Entry<Integer, World> es : worlds.entrySet()) {
			if (es.getValue().isPlayerOnline(player))
				return es.getKey();
		}
		return -1;
	}

	/**
	 * Gets the size of the channels.
	 * @return The size of the channels.
	 */
	public int size() {
		return channels.size();
	}

	/**
	 * Gets the size of the world.
	 * @return The size of the world.
	 */
	public int size(int index) {
		if (worlds.containsKey(index))
			return worlds.get(index).getPlayers().size();
		return 0;
	}

	@Override
	public String toString() {
		String buf = "";
		for (Channel channel : channels) {
			buf += channel + "\r\n";
		}
		return buf;
	}

	/**
	 * Gets the worlds.
	 * @return The worlds.
	 */
	public final Map<Integer, World> getWorlds() {
		return worlds;
	}

	/**
	 * Gets the players.
	 * @return The players.
	 */
	public final Map<String, Player> getPlayers(int index) {
		if (worlds.containsKey(index))
			return worlds.get(index).getPlayers();
		return null;
	}

	/**
	 * Gets the world.
	 * @param world The world.
	 * @return The world.
	 */
	public World getWorld(int world) {
		if (worlds.containsKey(world))
			return worlds.get(world);
		return null;
	}

	/**
	 * Gets the players rights.
	 * @param player The player.
	 * @return The player's rights.
	 */
	public int getRights(String player) {
		int rights = -1;
		final int world = get(player);
		if (world != -1)
			rights = worlds.get(world).getPlayers().get(player).getRights();
		return rights;
	}

}
