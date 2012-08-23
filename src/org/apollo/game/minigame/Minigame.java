package org.apollo.game.minigame;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apollo.game.minigame.event.JoinEvent;
import org.apollo.game.minigame.event.LeaveEvent;
import org.apollo.game.model.Player;

/**
 * Represents some kind of {@code game} that consists of players.
 * @author Steve
 */
public abstract class Minigame {

	/**
	 * The game's name.
	 */
	private final String game;

	/**
	 * The list of players.
	 */
	private final Map<Integer, ArrayList<Player>> players = new ConcurrentHashMap<Integer, ArrayList<Player>>();

	/**
	 * The minigame listeners.
	 */
	private final ArrayList<MinigameListener> listeners = new ArrayList<MinigameListener>();

	/**
	 * The minigame attributes.
	 */
	private final BitSet attributes = new BitSet();

	/**
	 * Initializes this game.
	 * @param game The game's name.
	 */
	public Minigame(String game) {
		this(game, 2);
	}

	/**
	 * Initializes this game.
	 * @param game The game's name.
	 * @param teams The teams to add.
	 */
	public Minigame(String game, int teams) {
		this.game = game;
		for (int i = 0; i - 1 < teams; i++)
			players.put(i, new ArrayList<Player>());
	}

	/**
	 * Adds a minigame listener.
	 * @param listener The minigame listener.
	 */
	public void addListener(MinigameListener listener) {
		listeners.add(listener);
	}

	/**
	 * Appends a player to the specified team.
	 * @param team The team.
	 * @param player The player to add.
	 * @return True if the player was added, false if otherwise.
	 */
	public boolean addPlayer(int team, Player player) {
		if (!players.get(team).contains(player)) {
			players.get(team).add(player);
			for (final MinigameListener listener : listeners)
				listener.playerAdded(new JoinEvent(player, team));
			return true;
		}
		return false;
	}

	/**
	 * Get the attribute's value.
	 * @param key The attribute.
	 * @return {@link Boolean} The {@code key}'s {@code value}
	 */
	public boolean getAttribute(int key) {
		return attributes.get(key);
	}

	/**
	 * Gets the game's name.
	 * @return The game's name.
	 */
	public String getGame() {
		return game;
	}

	/**
	 * Gets the list of current players.
	 * @return The list of current players.
	 */
	public Map<Integer, ArrayList<Player>> getPlayers() {
		return players;
	}

	/**
	 * Gets the list of current players.
	 * @param team The team.
	 * @return The list of current players.
	 */
	public ArrayList<Player> getPlayers(int team) {
		return players.get(team);
	}

	/**
	 * Gets a list of teams.
	 * @param teams The teams to get.
	 * @return The list of teams.
	 */
	public ArrayList<Player> getPlayers(int... teams) {
		final ArrayList<Player> players = new ArrayList<Player>();
		for (final int team : teams)
			players.addAll(this.players.get(team));
		return players;
	}

	/**
	 * Gets the team for the specified player.
	 * @param player The player.
	 * @return The team if found, -1 otherwise.
	 */
	public int getTeam(Player player) {
		for (final Entry<Integer, ArrayList<Player>> kv : players.entrySet())
			if (kv.getValue().contains(player))
				return kv.getKey();
		return -1;
	}

	/**
	 * Called when a player from this minigame disconnects.
	 * @param player The player that is disconnecting.
	 */
	protected void playerDisconnected(Player player) {
		for (final MinigameListener listener : listeners)
			listener.playerDisconnected(player);
	}

	/**
	 * The real pulser.
	 */
	protected abstract void pulse();

	/**
	 * Removes a player from the specified team.
	 * @param team The team.
	 * @param player The player to remove.
	 * @return True if the player was removed, false if otherwise.
	 */
	public boolean removePlayer(int team, Player player) {
		if (players.get(team).contains(player)) {
			players.get(team).remove(player);
			for (final MinigameListener listener : listeners)
				listener.playerRemoved(new LeaveEvent(player, team));
			return true;
		}
		return false;
	}

	/**
	 * Removes a player from the game.
	 * @param player The player to remove.
	 * @return True if the player was removed, false if otherwise.
	 */
	public boolean removePlayer(Player player) {
		final int team = getTeam(player);
		if (team != -1)
			if (players.get(team).contains(player)) {
				players.get(team).remove(player);
				for (final MinigameListener listener : listeners)
					listener.playerRemoved(new LeaveEvent(player, team));
				return true;
			}
		return false;
	}

	/**
	 * Set the attribute.
	 * @param key The key.
	 * @param value The value.
	 */
	public void setAttribute(int key, boolean value) {
		attributes.set(key, value);
	}

	/**
	 * Transfers this player to a different team.
	 * @param player The player to transfer.
	 * @param team The team to transfer into.
	 * @return True if successful, false if otherwise.
	 */
	public boolean transferTeam(Player player, int team) {
		final int current = getTeam(player);
		if (current != -1)
			return removePlayer(current, player) == addPlayer(team, player);
		return false;
	}

}
