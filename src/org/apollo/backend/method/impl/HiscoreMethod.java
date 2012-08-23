package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.game.model.Player;
import org.apollo.io.player.PlayerLoader;
import org.apollo.io.player.PlayerLoaderResponse;
import org.apollo.io.player.impl.DummyPlayerLoader;
import org.apollo.security.PlayerCredentials;

/**
 * An {@link Method} which displays hiscores for the user.
 * @author Steve
 */
public final class HiscoreMethod extends Method {

	/**
	 * The user.
	 */
	private final String user;

	/**
	 * Creates the hiscore method.
	 * @param user The user.
	 */
	public HiscoreMethod(String user) {
		this.user = user;
	}

	/**
	 * Gets the user.
	 * @return The user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Gets the player.
	 * @return The player.
	 */
	public Player getPlayer() {
		final PlayerCredentials credentials = new PlayerCredentials(user, "", 0, 0);
		final PlayerLoader loader = new DummyPlayerLoader();
		try {
			final PlayerLoaderResponse response = loader.loadPlayer(credentials);
			return response.getPlayer();
		} catch (Exception e) {
			return null;
		}
	}

}
