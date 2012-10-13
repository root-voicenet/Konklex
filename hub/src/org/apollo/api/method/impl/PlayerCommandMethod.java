package org.apollo.api.method.impl;

import org.apollo.api.method.Method;
import org.apollo.util.NameUtil;
import org.apollo.util.TextUtil;

/**
 * An {@link Method} which executes methods on the other server.
 * @author Steve
 */
public final class PlayerCommandMethod extends Method {
	
	/**
	 * The player.
	 */
	private final long player;
	
	/**
	 * The command.
	 */
	private final byte[] command;
	
	/**
	 * Creates a player command method.
	 * @param player The player.
	 * @param command The command.
	 */
	public PlayerCommandMethod(String player, String command) {
		this(NameUtil.encodeBase37(player), command);
	}
	
	/**
	 * Creates a player command method.
	 * @param player The player.
	 * @param command The command.
	 */
	public PlayerCommandMethod(long player, String command) {
		command = TextUtil.filterInvalidCharacters(command);
		command = TextUtil.capitalize(command);
		byte[] temp = new byte[command.length()];
		TextUtil.compress(command, temp);
		this.player = player;
		this.command = temp;
	}
	
	/**
	 * Creates a player command method.
	 * @param player The player.
	 * @param command The command.
	 */
	public PlayerCommandMethod(long player, byte[] command) {
		this.player = player;
		this.command = command;
	}
	
	/**
	 * Creates a player command method.
	 * @param player The player.
	 * @param command The command.
	 */
	public PlayerCommandMethod(String player, byte[] command) {
		this(NameUtil.encodeBase37(player), command);
	}
	
	/**
	 * Gets the player.
	 * @return The player.
	 */
	public long getPlayer() {
		return player;
	}
	
	/**
	 * Gets the message.
	 * @return The message.
	 */
	public byte[] getCommand() {
		return command;
	}

}
