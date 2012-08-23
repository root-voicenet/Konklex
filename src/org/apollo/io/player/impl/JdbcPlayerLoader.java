package org.apollo.io.player.impl;

import javax.sql.rowset.CachedRowSet;

import org.apollo.io.player.PlayerLoader;
import org.apollo.io.player.PlayerLoaderResponse;
import org.apollo.security.PlayerCredentials;
import org.apollo.util.MysqlUtil;

/**
 * An {@link PlayerLoader} that utilizes a {@code jdbc} database.
 * @author Steve
 */
public final class JdbcPlayerLoader implements PlayerLoader {

    /*
     * (non-Javadoc)
     * @see org.apollo.io.player.PlayerLoader#loadPlayer(org.apollo.security.
     * PlayerCredentials)
     */
    @Override
    public PlayerLoaderResponse loadPlayer(PlayerCredentials credentials) throws Exception {
	final CachedRowSet rs = MysqlUtil.query("");
	while (rs.next()) {
	    // Do stuff
	}
	return null;
    }
}
