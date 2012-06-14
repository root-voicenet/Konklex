package org.apollo.game.action.impl;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.model.Character;
import org.apollo.game.model.Position;
import org.apollo.game.pf.AStarPathFinder;
import org.apollo.game.pf.Path;
import org.apollo.game.pf.PathFinder;
import org.apollo.game.pf.TileMapBuilder;

/**
 * An {@link DistancedAction} for when a player follows another player.
 * @author Steve
 */
public final class PlayerFollowAction extends DistancedAction<Character> {

    /**
     * The acquaintance.
     */
    private final Character acquaintance;

    /**
     * Create a new player follow action.
     * @param character The character.
     * @param acquaintance The acquaintance.
     */
    public PlayerFollowAction(Character character, Character acquaintance) {
	super(1, true, character, acquaintance.getPosition(), Position.MAX_DISTANCE);
	this.acquaintance = acquaintance;
    }

    @Override
    public void executeAction() {
	final Character player = getCharacter();
	final Character other = acquaintance;
	if (player.isDead() || !player.isActive() || other.isDead() || !other.isActive()) {
	    stop();
	} else {
	    final int distanceX = player.getPosition().getX() - other.getPosition().getX();
	    final int distanceY = player.getPosition().getY() - other.getPosition().getY();
	    if (player.getPosition().getHeight() != other.getPosition().getHeight()) {
		stop();
	    } else if (distanceX > Position.MAX_DISTANCE || distanceX < -1 - Position.MAX_DISTANCE
		    || distanceY > Position.MAX_DISTANCE || distanceY < -1 - Position.MAX_DISTANCE) {
		stop();
	    } else {
		player.startFacing(other.getIndex());
		PathFinder finder = new AStarPathFinder();
		TileMapBuilder builder = new TileMapBuilder(player.getPosition(), 32);
		Path path = finder.findPath(player.getPosition(), 32, builder.build(),
			player.getPosition().getLocalX(), player.getPosition().getLocalY(), other.getPosition()
				.getLocalX(player.getPosition()), other.getPosition().getLocalY(player.getPosition()));
		if (path == null) {
		    System.out.println("Path is null");
		    return;
		}
		Position position = new Position(path.getPoints().poll().getX(), path.getPoints().poll().getY());
		player.getWalkingQueue().addStep(position);
		System.out.println(position);
		player.stopFacing();
	    }
	}
    }
}