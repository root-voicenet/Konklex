package org.apollo.game.action.impl;

import org.apollo.game.action.DistancedAction;
import org.apollo.game.model.Character;
import org.apollo.game.model.Position;
import org.apollo.game.model.WalkingQueue;

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
		super(0, true, character, acquaintance.getPosition(), Position.MAX_DISTANCE);
		this.acquaintance = acquaintance;
	}

	@Override
	public void executeAction() {
		final Character player = getCharacter();
		final Character other = acquaintance;
		if (player.isDead() || !player.isActive() || other.isDead() || !other.isActive()) {
			stop();
		}
		else {
			int playerX = player.getPosition().getX();
			int playerY = player.getPosition().getY();
			int acquaintanceX = acquaintance.getPosition().getX();
			int acquaintanceY = acquaintance.getPosition().getY();

			if (!player.getPosition().isWithinDistance(acquaintance.getPosition(), Position.MAX_DISTANCE)) {
				player.sendMessage("You are too far away!");
				return;
			}

			if (acquaintanceY == playerY && acquaintanceX == playerX) {
				walkTo(0, getMove(playerY, acquaintanceY - 1));
			}
			else if (acquaintanceY > playerY && acquaintanceX == playerX) {
				walkTo(0, getMove(playerY, acquaintanceY - 1));
			}
			else if (acquaintanceY < playerY && acquaintanceX == playerX) {
				walkTo(0, getMove(playerY, acquaintanceY + 1));
			}
			else if (acquaintanceX > playerX && acquaintanceY == playerY) {
				walkTo(getMove(playerX, acquaintanceX - 1), 0);
			}
			else if (acquaintanceX < playerX && acquaintanceY == playerX) {
				walkTo(getMove(playerX, acquaintanceX + 1), 0);
			}
			else if (acquaintanceX < playerX && acquaintanceY < playerY) {
				walkTo(getMove(playerX, acquaintanceX + 1), getMove(playerY, acquaintanceY + 1));
			}
			else if (acquaintanceX > playerX && acquaintanceY > playerY) {
				walkTo(getMove(playerX, acquaintanceX - 1), getMove(playerY, acquaintanceY - 1));
			}
			else if (acquaintanceX < playerX && acquaintanceY > playerY) {
				walkTo(getMove(playerX, acquaintanceX + 1), getMove(playerY, acquaintanceY - 1));
			}
			else if (acquaintanceX > playerX && acquaintanceY < playerY) {
				walkTo(getMove(playerX, acquaintanceX - 1), getMove(playerY, acquaintanceY + 1));
			}
		}
	}

	/**
	 * Gets the distance between the specified distances.
	 * @param i The first distance.
	 * @param j The second distance.
	 * @return The distance between the specified distances.
	 */
	private int getMove(int i, int j) {
		if (i - j == 0) {
			return 0;
		}
		if (i - j < 0) {
			return 1;
		}
		return i - j <= 0 ? 0 : -1;
	}

	/**
	 * Adds a step to the characters walking queue.
	 * @param x The amount of x coordinates.
	 * @param y The amount of y coordinates.
	 */
	private void walkTo(int x, int y) {
		WalkingQueue queue = getCharacter().getWalkingQueue();
		Position position = acquaintance.getPosition();
		int absX = getCharacter().getPosition().getX();
		int absY = getCharacter().getPosition().getY();
		queue.addFirstStep(new Position(absX + x, absY + y));
		if (!queue.getRunning()) {
			queue.setRunning(queue.getRunningQueue());
		}
		getCharacter().turnTo(position);
	}
}