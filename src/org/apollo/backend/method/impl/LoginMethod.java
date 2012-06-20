package org.apollo.backend.method.impl;

import org.apollo.backend.method.Method;
import org.apollo.game.model.Player;
import org.apollo.io.player.PlayerLoader;
import org.apollo.io.player.PlayerLoaderResponse;
import org.apollo.io.player.impl.DummyPlayerLoader;
import org.apollo.security.PlayerCredentials;

/**
 * An {@link Method} that logs a player in.
 * @author Steve
 */
public final class LoginMethod extends Method {

    /**
     * The username.
     */
    private final String user;

    /**
     * The password.
     */
    private final String pass;

    /**
     * Creates the new login method.
     * @param user The username.
     * @param pass The password.
     */
    public LoginMethod(String user, String pass) {
	this.user = user;
	this.pass = pass;
    }

    /**
     * Checks if the player can login.
     * @return The player.
     * @throws Exception If the players file could not be loaded.
     */
    public Player getPlayer() throws Exception {
	PlayerLoader loader = new DummyPlayerLoader();
	PlayerCredentials credentials = new PlayerCredentials(user, pass, 0, 0);
	final PlayerLoaderResponse response = loader.loadPlayer(credentials);
	return response.getPlayer();
    }

}
