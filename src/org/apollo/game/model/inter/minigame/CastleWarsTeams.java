package org.apollo.game.model.inter.minigame;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import org.apollo.game.event.impl.ConfigEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.inter.minigame.util.GameType;
import org.apollo.game.model.inter.minigame.util.Team;

/**
 * Build the {@code CastleWar}'s teams.
 * @author Steve (Buroa)
 */
public final class CastleWarsTeams {

	/*
	 * Attribute Table: 0 = Side door unlocked 1 = Rock 1 collapsed 2 = Rock 2 collapsed 3 = Catapault operational 4 = Flag taken
	 */
	/**
	 * The lobby position.
	 */
	private final Position lobby;

	/**
	 * The game lobby position.
	 */
	private final Position game;

	/**
	 * The flag position.
	 */
	private final Position pole;

	/**
	 * The {@code Team}'s players.
	 */
	private final Map<GameType, ArrayList<Player>> players = new HashMap<GameType, ArrayList<Player>>();

	/**
	 * Holds the @{code Team} attributes.
	 */
	private final BitSet attributes = new BitSet(5);

	/**
	 * The score.
	 */
	private int score = 0;

	/**
	 * The health of main door.
	 */
	private int health;

	/**
	 * The flag id.
	 */
	private final int flag;

	/**
	 * The flag bases.
	 */
	private final int baseon, baseoff;

	/**
	 * Create a new {@code CastleWar}'s {@link Team}.
	 * @param lobby The pre-game lobby position.
	 * @param game The lobby position.
	 * @param pole The flag position.
	 * @param baseon The flag base (on)
	 * @param baseoff The flag base (off)
	 * @param flag The flag id.
	 */
	public CastleWarsTeams(Position lobby, Position game, Position pole, int baseon, int baseoff, int flag) {
		this.lobby = lobby;
		this.game = game;
		this.pole = pole;
		this.baseon = baseon;
		this.baseoff = baseoff;
		this.flag = flag;
		players.put(GameType.ACTIVE, new ArrayList<Player>());
		players.put(GameType.WAITING, new ArrayList<Player>());
	}

	/**
	 * Add the player to the team.
	 * @param player The player.
	 * @param game The game type.
	 * @return {@link Boolean} True if added, false if not.
	 */
	public boolean addPlayer(Player player, GameType game) {
		if (!getPlayers(game).contains(player)) {
			players.get(game).add(player);
			player.send(new ConfigEvent(380, CastleWars.getInstance().getTick() / 60 == 0 ? 1 : CastleWars.getInstance().getTick() / 60));
			if (game == GameType.WAITING) {
				player.getInterfaceSet().openWalkable(11479);
			} else
				if (game == GameType.ACTIVE) {
					CastleWars.getInstance().sendConfig(player);
					player.getInterfaceSet().openWalkable(11146);
					CastleWars.getInstance().updateFlag(player);
				}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add a point to the current score.
	 */
	public void addPoint() {
		this.score++;
	}

	/**
	 * Clears the current game type.
	 * @param game The game type.
	 */
	public void clear(GameType game) {
		players.get(game).clear();
		if (game == GameType.ACTIVE) {
			attributes.clear();
		}
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
	 * The flag stand, not clickable.
	 * @return {@link Integer} The base off id.
	 */
	public int getBaseOff() {
		return baseoff;
	}

	/**
	 * The flag stand, clickable.
	 * @return {@link Integer} The base on id.
	 */
	public int getBaseOn() {
		return baseon;
	}

	/**
	 * The flag id.
	 * @return {@link Integer} The flag id.
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * The ingame lobby.
	 * @return {@link Position} The lobby's position.
	 */
	public Position getGame() {
		return game;
	}

	/**
	 * The main doors health.
	 * @return {@link Integer} The health.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * The lobby.
	 * @return {@link Position} The lobby's position.
	 */
	public Position getLobby() {
		return lobby;
	}

	/**
	 * The team of players.
	 * @param game The game type.
	 * @return {@link ArrayList} The team.
	 */
	public ArrayList<Player> getPlayers(GameType game) {
		return players.get(game);
	}

	/**
	 * The flag position.
	 * @return {@link Position} The flag's position.
	 */
	public Position getPole() {
		return pole;
	}

	/**
	 * The score.
	 * @return {@link Integer} The score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Removes the player from the team.
	 * @param player The player.
	 * @param game The game type.
	 * @return {@link Boolean} True if added, false if not.
	 */
	public boolean removePlayer(Player player, GameType game) {
		if (getPlayers(game).contains(player)) {
			players.get(game).remove(player);
			player.getInterfaceSet().openWalkable(-1);
			return true;
		} else {
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
	 * Set the door health.
	 * @param health The health of the door.
	 */
	public void setHealth(int health) {
		this.health = health;
	}
}
