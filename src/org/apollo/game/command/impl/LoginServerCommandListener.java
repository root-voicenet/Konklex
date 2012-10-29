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
import org.apollo.util.TextUtil;

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
		int diffX = TextUtil.random(3);
		int diffY = TextUtil.random(3);
		
		int gotoX = player.getPosition().getX() + diffX;
		int gotoY = player.getPosition().getY() + diffY;
		 
		boolean canWalk = true;
		 
		int radius = 16;
		 
		int x2 = gotoX - player.getPosition().getX() + radius;
		int y2 = gotoY - player.getPosition().getY() + radius;
		 
		TileMapBuilder bldr = new TileMapBuilder(player.getPosition(), radius);
		TileMap map = bldr.build();
		 
		PathFinder pf = new AStarPathFinder();
		Path p = pf.findPath(player.getPosition(), radius, map,
		        radius, radius, x2, y2);
		 
		if (p == null) {
			canWalk = false;
		}
		 
		if(canWalk) {
		    player.getWalkingQueue().clear();
		    for (Point p2 : p.getPoints()) {
		    	final Position walk = new Position(p2.getX(), p2.getY());
		    	
		    	System.out.println(walk.toString());
		    	
		        player.getWalkingQueue().addStep(walk);
		    }
		}
	}

}
