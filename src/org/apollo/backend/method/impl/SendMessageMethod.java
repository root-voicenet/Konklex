package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;

/**
 * An {@link Method} that sends a line of text to the specified player.
 * 
 * @author Steve
 */
public final class SendMessageMethod extends Method {

	/**
	 * The player.
	 */
	private final String player;

	/**
	 * The message.
	 */
	private final String message;

	/**
	 * Create the new send message method.
	 * 
	 * @param player
	 *            The player.
	 * @param message
	 *            The message.
	 * @param key
	 *            The key.
	 */
	public SendMessageMethod(String player, String message, String key) {
		super(key);
		this.player = player;
		this.message = message;
	}

	/**
	 * Gets the message.
	 * 
	 * @return The message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the player.
	 * 
	 * @return The player.
	 */
	public String getPlayer() {
		return player;
	}

}
