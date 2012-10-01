package org.apollo.api.method.impl;

import org.apollo.api.method.Method;
import org.apollo.util.NameUtil;

/**
 * An {@link Method} which executes commands for players.
 * @author Steve
 */
public final class PlayerCommandMethod extends Method {
	
	/**
	 * The player.
	 */
	private final String player;
	
	/**
	 * The command.
	 */
	private final String command;
	
	/**
	 * Creates a player command method.
	 * @param player The player.
	 * @param command The command.
	 */
	public PlayerCommandMethod(long player, String command) {
		this.player = NameUtil.decodeBase37(player);
		this.command = command;
	}
	
	/**
	 * Gets the player.
	 * @return The player.
	 */
	public String getPlayer() {
		return player;
	}
	
	/**
	 * Gets the message.
	 * @return The message.
	 */
	public String getCommand() {
		return command;
	}

}
