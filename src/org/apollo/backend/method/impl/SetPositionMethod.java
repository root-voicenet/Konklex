package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.game.model.Position;

/**
 * An {@link Method} that sets a players position.
 * @author Steve
 */
public final class SetPositionMethod extends Method {

	/**
	 * The player to be positioned.
	 */
	private final String player;

	/**
	 * The position to be set.
	 */
	private final Position position;

	/**
	 * Creates the set position method.
	 * @param player The player that is to be positioned.
	 * @param position The position to be set.
	 * @param key The key that was requested.
	 */
	public SetPositionMethod(String player, Position position, String key) {
		super(key);
		this.player = player;
		this.position = position;
	}

	/**
	 * Gets the player to be positioned.
	 * @return The player to be positioned.
	 */
	public String getPlayer() {
		return player;
	}

	/**
	 * Gets the position to be set.
	 * @return The position to be set.
	 */
	public Position getPosition() {
		return position;
	}

}
