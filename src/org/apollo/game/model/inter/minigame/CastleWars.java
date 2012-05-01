package org.apollo.game.model.inter.minigame;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import org.apollo.game.event.impl.ConfigEvent;
import org.apollo.game.model.EquipmentConstants;
import org.apollo.game.model.Item;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.def.StaticObjectDefinition;
import org.apollo.game.model.inter.minigame.util.GameType;
import org.apollo.game.model.inter.minigame.util.Team;
import org.apollo.game.scheduling.impl.CastleWarsTask;

/**
 * Holds the castle wars minigame.
 * @author Steve (Buroa)
 */
public final class CastleWars {

	/*
	 * Attribute Table: 0 = (Both) Game start 1 = Active interface update 2 = (Both) Time update 3 = Sara flag taken update 4 = Zamorak flag taken update
	 */
	/**
	 * The castle wars instance.
	 */
	private static CastleWars instance;

	/**
	 * Returns the {@link CastleWars} instance.
	 * @return {@link CastleWars} The instance.
	 */
	public static CastleWars getInstance() {
		if (instance == null) {
			instance = new CastleWars();
			CastleWarsTask.start();
		}
		return instance;
	}

	/**
	 * The current list of players that are playing {@code CastleWars}.
	 */
	private final Map<Team, CastleWarsTeams> teams = new HashMap<Team, CastleWarsTeams>();

	/**
	 * The global {@code CastleWar}'s attributes.
	 */
	private final BitSet attributes = new BitSet(4);

	/**
	 * The ticks in seconds.
	 */
	private int tick = 180;

	/**
	 * Create a new game with the specified teams.
	 */
	private CastleWars() {
		teams.put(Team.SARADOMIN, new CastleWarsTeams(new Position(2382, 9488, 0), new Position(2427, 3076, 1), new Position(2429, 3074, 3), 4902, 4377, 4037));
		teams.put(Team.ZAMORAK, new CastleWarsTeams(new Position(2423, 9522, 0), new Position(2372, 3131, 1), new Position(2370, 3133, 3), 4903, 4378, 4039));
	}

	/**
	 * Add a player to a specified team.
	 * @param team The team.
	 * @param game The game type.
	 * @param player The player.
	 * @return {@link Boolean} True if added, false if not.
	 */
	public boolean addPlayer(Team team, GameType game, Player player) {
		return teams.get(team).addPlayer(player, game);
	}

	/**
	 * Capture the flag.
	 * @param player The player that is capturing.
	 */
	public void captureFlag(Player player) {
		if (getPlayers(GameType.ACTIVE).contains(player)) {
			Team team = getTeam(player, GameType.ACTIVE) == Team.SARADOMIN ? Team.ZAMORAK : Team.SARADOMIN;
			if (!teams.get(team).getAttribute(4)) {
				if (player.getInventory().freeSlots() >= 2) {
					player.getInventory().add(player.getEquipment().set(EquipmentConstants.WEAPON, new Item(teams.get(team).getFlag())));
					player.getInventory().add(player.getEquipment().set(EquipmentConstants.SHIELD, null));
					teams.get(team).setAttribute(4, true);
					setAttribute(team == Team.SARADOMIN ? 3 : 4, true);
					setAttribute(1, true);
				}
			}
		}
	}

	/**
	 * Clear the teams.
	 * @param game The game type.
	 */
	public void clear(GameType game) {
		tick = getAttribute(0) ? 1200 : 180;
		teams.get(Team.SARADOMIN).clear(game);
		teams.get(Team.ZAMORAK).clear(game);
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
	 * Returns the current players playing {@code CastleWars}.
	 * @param game The game type.
	 * @return {@link ArrayList} The players
	 */
	public ArrayList<Player> getPlayers(GameType game) {
		ArrayList<Player> players = new ArrayList<Player>();
		players.addAll(this.teams.get(Team.SARADOMIN).getPlayers(game));
		players.addAll(this.teams.get(Team.ZAMORAK).getPlayers(game));
		return players;
	}

	/**
	 * Returns the position of the {@code active} game.
	 * @param team The team.
	 * @param game The team.
	 * @return {@link Position}
	 */
	public Position getPosition(Team team, GameType game) {
		if (game == GameType.ACTIVE) {
			return teams.get(team).getGame();
		}
		return teams.get(team).getLobby();
	}

	/**
	 * Gets the players team.
	 * @param player The player.
	 * @param game The game.
	 * @return {@link Team} The team the player belongs to.
	 */
	public Team getTeam(Player player, GameType game) {
		if (teams.get(Team.SARADOMIN).getPlayers(game).contains(player)) {
			return Team.SARADOMIN;
		} else
			if (teams.get(Team.ZAMORAK).getPlayers(game).contains(player)) {
				return Team.ZAMORAK;
			} else {
				return null;
			}
	}

	/**
	 * Returns the current team.
	 * @param team The team to find.
	 * @param game The lobby.
	 * @return {@link ArrayList} The team.
	 */
	public ArrayList<Player> getTeam(Team team, GameType game) {
		return teams.get(team).getPlayers(game);
	}

	/**
	 * Get the current teams.
	 * @return {@link Map} The current castle wars teams.
	 */
	public Map<Team, CastleWarsTeams> getTeams() {
		return teams;
	}

	/**
	 * Get the ticker time.
	 * @return {@link Integer} The time from the ticker.
	 */
	public int getTick() {
		return tick;
	}

	/**
	 * The ticker, called every second.
	 */
	public void process() {
		if (getAttribute(0)) {
			if (tick <= 0) {
				for (Player player : getPlayers(GameType.ACTIVE)) {
					player.teleport(new Position(2440, 3090, 0), false);
					player.getInterfaceSet().openWalkable(-1);
				}
				setAttribute(0, false);
				clear(GameType.ACTIVE);
			} else {
				ArrayList<Player> players = getPlayers(GameType.ACTIVE);
				if (getAttribute(1)) {
					for (Player player : players) {
						sendConfig(player);
					}
					setAttribute(1, false);
				}
				if (getAttribute(2)) {
					int time = tick / 60 == 0 ? 1 : tick / 60;
					for (Player player : players) {
						player.send(new ConfigEvent(380, time));
					}
					for (Player player : getPlayers(GameType.WAITING)) {
						player.send(new ConfigEvent(380, time));
					}
					setAttribute(2, false);
				}
				if (getAttribute(3)) {
					for (Player player : players) {
						updateFlag(player, Team.SARADOMIN);
					}
					setAttribute(3, false);
				}
				if (getAttribute(4)) {
					for (Player player : players) {
						updateFlag(player, Team.ZAMORAK);
					}
					setAttribute(4, false);
				}
				tick--;
			}
		} else
			if (tick <= 0) {
				for (Player player : getPlayers(GameType.WAITING)) {
					Team team = getTeam(player, GameType.WAITING);
					if (addPlayer(team, GameType.ACTIVE, player)) {
						player.teleport(teams.get(team).getGame(), false);
					}
				}
				setAttribute(0, true);
				clear(GameType.WAITING);
			} else {
				if (getAttribute(2)) {
					int time = tick / 60 == 0 ? 1 : tick / 60;
					for (Player player : getPlayers(GameType.WAITING)) {
						player.send(new ConfigEvent(380, time));
					}
					setAttribute(2, false);
				}
				tick--;
			}
	}

	/**
	 * Remove a player from a specified team.
	 * @param team The team.
	 * @param game The game type.
	 * @param player The player.
	 * @return {@link Boolean} True if removed, false if not.
	 */
	public boolean removePlayer(Team team, GameType game, Player player) {
		return teams.get(team).removePlayer(player, game);
	}

	/**
	 * Return the flag.
	 * @param player The player that is return the flag.
	 */
	public void returnFlag(Player player) {
		if (getPlayers(GameType.ACTIVE).contains(player)) {
			Team team = getTeam(player, GameType.ACTIVE) == Team.SARADOMIN ? Team.ZAMORAK : Team.SARADOMIN;
			if (teams.get(team).getAttribute(4)) {
				Item weapon = player.getEquipment().get(EquipmentConstants.WEAPON);
				if (weapon != null && weapon.getId() == teams.get(team).getFlag()) {
					player.getEquipment().set(EquipmentConstants.WEAPON, null);
					teams.get(getTeam(player, GameType.ACTIVE)).addPoint();
					teams.get(team).setAttribute(4, false);
					setAttribute(1, true);
					setAttribute(3, true);
				}
			}
		}
	}

	/**
	 * Send the current config update to the specified player.
	 * @param player The player to recieve the update.
	 */
	public void sendConfig(Player player) {
		Team team = getTeam(player, GameType.ACTIVE);
		CastleWarsTeams sara = teams.get(Team.SARADOMIN), zammy = teams.get(Team.ZAMORAK);
		int config = zammy.getHealth();
		if (team == Team.ZAMORAK ? zammy.getAttribute(0) : sara.getAttribute(0)) {
			config += 128;
		}
		if (!sara.getAttribute(1)) {
			config += 256;
		}
		if (!sara.getAttribute(2)) {
			config += 512;
		}
		if (team == Team.ZAMORAK ? !zammy.getAttribute(3) : !sara.getAttribute(3)) {
			config += 1024;
		}
		if (team == Team.ZAMORAK ? zammy.getAttribute(4) : sara.getAttribute(4)) {
			config += 2097152;
		}
		config += 16777216 * (team == Team.ZAMORAK ? zammy.getScore() : sara.getScore());
		player.send(new ConfigEvent(team == Team.ZAMORAK ? 377 : 378, config));
		config = sara.getHealth();
		if (team == Team.ZAMORAK ? zammy.getAttribute(0) : sara.getAttribute(0)) {
			config += 128;
		}
		if (!zammy.getAttribute(1)) {
			config += 256;
		}
		if (!zammy.getAttribute(2)) {
			config += 512;
		}
		if (!sara.getAttribute(3)) {
			config += 1024;
		}
		if (team == Team.ZAMORAK ? sara.getAttribute(4) : zammy.getAttribute(4)) {
			config += 2097152;
		}
		config += 16777216 * (team == Team.ZAMORAK ? sara.getScore() : zammy.getScore());
		player.send(new ConfigEvent(team == Team.ZAMORAK ? 378 : 377, config));
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
	 * Sets the tick.
	 * @param tick The tick to set.
	 */
	public void setTick(int tick) {
		this.tick = tick;
	}

	/**
	 * Updates the flag positions for the player.
	 * @param player The player.
	 */
	public void updateFlag(Player player) {
		updateFlag(player, Team.SARADOMIN);
		updateFlag(player, Team.ZAMORAK);
	}

	/**
	 * Updates the flag positions for the specified team.
	 * @param player The player to receive the update.
	 * @param team The team to look for the flag positions.
	 */
	public void updateFlag(Player player, Team team) {
		if (getPlayers(GameType.ACTIVE).contains(player)) {
			if (teams.get(team).getAttribute(4)) {
				player.getObjectSet().add(new StaticObjectDefinition(teams.get(team).getPole(), teams.get(team).getBaseOff(), team == Team.ZAMORAK ? -1 : -3, 10));
			} else {
				player.getObjectSet().remove(new StaticObjectDefinition(teams.get(team).getPole(), teams.get(team).getBaseOn(), team == Team.ZAMORAK ? -1 : -3, 10));
			}
		}
	}
}
