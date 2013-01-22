package org.apollo.game.minigame;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apollo.game.minigame.event.DeathEvent;
import org.apollo.game.minigame.event.JoinEvent;
import org.apollo.game.minigame.event.LeaveEvent;
import org.apollo.game.model.Character;

/**
 * Represents some kind of {@code game} that consists of characters.
 * @author Steve
 */
public abstract class Minigame {

	/**
	 * The game's name.
	 */
	private final String game;

	/**
	 * The list of characters.
	 */
	private final Map<Integer, ArrayList<Character>> characters = new ConcurrentHashMap<Integer, ArrayList<Character>>();

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
		for (int i = 0; i - 1 < teams; i++) {
			characters.put(i, new ArrayList<Character>());
		}
	}

	/**
	 * Appends a player to the specified team.
	 * @param team The team.
	 * @param player The player to add.
	 * @return True if the player was added, false if otherwise.
	 */
	public boolean addCharacter(int team, Character player) {
		synchronized (this) {
			if (!characters.get(team).contains(player)) {
				characters.get(team).add(player);
				for (final MinigameListener listener : listeners) {
					listener.playerAdded(new JoinEvent(player, team));
				}
				return true;
			}
			return false;
		}
	}

	/**
	 * Adds a minigame listener.
	 * @param listener The minigame listener.
	 */
	public void addListener(MinigameListener listener) {
		listeners.add(listener);
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
	 * Gets the list of current characters.
	 * @return The list of current characters.
	 */
	public Map<Integer, ArrayList<Character>> getCharacters() {
		return Collections.unmodifiableMap(characters);
	}

	/**
	 * Gets a list of teams.
	 * @param teams The teams to get.
	 * @return The list of teams.
	 */
	public List<Character> getCharacters(int... teams) {
		final List<Character> characters = new ArrayList<Character>();
		for (final int team : teams) {
			characters.addAll(this.characters.get(team));
		}
		return Collections.unmodifiableList(characters);
	}

	/**
	 * Gets the game's name.
	 * @return The game's name.
	 */
	public String getGame() {
		return game;
	}

	/**
	 * Gets the team for the specified player.
	 * @param player The player.
	 * @return The team if found, -1 otherwise.
	 */
	public int getTeam(Character player) {
		synchronized (this) {
			if (player == null)
				return -1;
			for (final Entry<Integer, ArrayList<Character>> kv : getCharacters().entrySet())
				if (kv.getValue().contains(player))
					return kv.getKey();
			return -1;
		}
	}

	/**
	 * Called when a player from this minigame dies.
	 * @param player The player that is dying.
	 * @param source The source that killed this player.
	 */
	protected void playerDied(Character player, Character source) {
		final int playerTeam = getTeam(player);
		final int sourceTeam = getTeam(source);
		if (playerTeam != -1 && sourceTeam != -1) {
			for (final MinigameListener listener : listeners) {
				listener.playerDied(new DeathEvent(player, source, playerTeam, sourceTeam));
			}
		}
	}

	/**
	 * Called when a player from this minigame disconnects.
	 * @param player The player that is disconnecting.
	 */
	protected void playerDisconnected(Character player) {
		for (final MinigameListener listener : listeners) {
			if (player.isControlling()) {
				listener.playerDisconnected(player);
			}
		}
	}

	/**
	 * The real pulser.
	 */
	protected abstract void pulse();

	/**
	 * Removes a player from the game.
	 * @param player The player to remove.
	 * @return True if the player was removed, false if otherwise.
	 */
	public boolean removeCharacter(Character player) {
		synchronized (this) {
			final int team = getTeam(player);
			if (team != -1)
				if (characters.get(team).contains(player)) {
					characters.get(team).remove(player);
					for (final MinigameListener listener : listeners) {
						listener.playerRemoved(new LeaveEvent(player, team));
					}
					return true;
				}
			return false;
		}
	}

	/**
	 * Removes a player from the specified team.
	 * @param team The team.
	 * @param player The player to remove.
	 * @return True if the player was removed, false if otherwise.
	 */
	public boolean removeCharacter(int team, Character player) {
		synchronized (this) {
			if (characters.get(team).contains(player)) {
				characters.get(team).remove(player);
				for (final MinigameListener listener : listeners) {
					listener.playerRemoved(new LeaveEvent(player, team));
				}
				return true;
			}
			return false;
		}
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
	public boolean transferTeam(Character player, int team) {
		final int current = getTeam(player);
		if (current != -1)
			return removeCharacter(current, player) && addCharacter(team, player);
		return false;
	}

}