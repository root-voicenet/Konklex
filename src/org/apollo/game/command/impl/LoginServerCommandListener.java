package org.apollo.game.command.impl;

import org.apollo.game.command.Command;
import org.apollo.game.command.PrivilegedCommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.Position;
import org.apollo.game.pf.AStarPathFinder;
import org.apollo.game.pf.Path;
import org.apollo.game.pf.PathFinder;
import org.apollo.game.pf.Point;
import org.apollo.game.pf.TileMap;
import org.apollo.game.pf.TileMapBuilder;

/**
 * An {@link PrivilegedCommandListener} for the login server commands.
 * @author Steve
 */
public final class LoginServerCommandListener extends PrivilegedCommandListener {

	/**
	 * Creates the login server command listener.
	 */
	public LoginServerCommandListener() {
		super(PrivilegeLevel.DEVELOPER);
	}

	@Override
	public void executePrivileged(Player player, Command command) {
		int radius = 8;
        
        int x = 3200 - player.getPosition().getX() + radius;
        int y = 3200 - player.getPosition().getY() + radius;
                                                       
        TileMapBuilder bldr = new TileMapBuilder(player.getPosition(), radius);
        TileMap map = bldr.build();
       
        PathFinder pf = new AStarPathFinder();
        Path p = pf.findPath(player.getPosition(), radius, map, radius, radius, x, y);
       
        if(p == null) return;
                                                       
        player.getWalkingQueue().clear();
        for(Point p2 : p.getPoints()) {
                player.getWalkingQueue().addStep(new Position(p2.getX(), p2.getY()));
        }
	}

}
