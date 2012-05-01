package org.apollo.game.action.impl;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.model.Character;
import org.apollo.game.model.Position;

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
		Character player = getCharacter();
		Character other = acquaintance;
		if (player.isDead() || !player.isActive() || other.isDead() || !other.isActive()) {
			stop();
		} else {
			int distanceX = player.getPosition().getX() - other.getPosition().getX();
			int distanceY = player.getPosition().getY() - other.getPosition().getY();
			if (player.getPosition().getHeight() != other.getPosition().getHeight()) {
				stop();
			} else {
				if (distanceX > Position.MAX_DISTANCE || distanceX < -1 - Position.MAX_DISTANCE || distanceY > Position.MAX_DISTANCE || distanceY < -1 - Position.MAX_DISTANCE) {
					stop();
				} else {
					player.startFacing(other.getIndex());
					player.getWalkingQueue().addStep(other.getPosition());
					player.stopFacing();
				}
			}
		}
	}
}