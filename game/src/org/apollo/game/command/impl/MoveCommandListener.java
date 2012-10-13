package org.apollo.game.command.impl;

import org.apollo.game.command.Command;
import org.apollo.game.command.CommandListener;
import org.apollo.game.model.Player;
import org.apollo.game.model.Position;
import org.apollo.game.model.Skill;
import org.apollo.game.model.World;
import org.apollo.game.scheduling.ScheduledTask;
import org.apollo.game.sync.block.SynchronizationBlock;

/**
 * Implements a {@code ::move} command that moves a player.
 * @author Steve
 */
public final class MoveCommandListener implements CommandListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.game.command.CommandListener#execute(org.apollo.game.model .Player,
	 * org.apollo.game.command.Command)
	 */
	@Override
	public void execute(Player player, Command command) {
		final String[] arguments = command.getArguments();

		if (arguments.length == 7) {
			int x = Integer.parseInt(arguments[0]);
			int y = Integer.parseInt(arguments[1]);
			int speed1 = Integer.parseInt(arguments[2]);
			int speed2 = Integer.parseInt(arguments[3]);
			int time = Integer.parseInt(arguments[4]);
			int xp = Integer.parseInt(arguments[5]);
			int walkAnim = Integer.parseInt(arguments[6]);
			setForceMovement(player, x, y, speed1, speed2, time, xp, walkAnim);
		}
		else {
			player.sendMessage("Syntax: ::move [x] [y] [speed] [speed] [time] [xp] [animation]");
		}
	}

	public void setForceMovement(final Player player, final int x, final int y, final int speed1, final int speed2,
			final int time, final int xp, final int walkAnim) {
		player.getWalkingQueue().clear();
		if (walkAnim > 0) {
			player.getAppearance().setRunAnimation(walkAnim);
			player.getAppearance().setWalkAnimation(walkAnim);
			player.setAppearance(player.getAppearance());
		}
		int direction = 2;
		if (x > 0) {
			direction = 1;
		}
		else if (x < 0) {
			direction = 3;
		}
		else if (y > 0) {
			direction = 0;
		}
		// player.movePlayer(player.getPosition());
		final int endX = player.getPosition().getX() + x;
		final int endY = player.getPosition().getY() + y;
		final int endZ = player.getPosition().getHeight();
		final int dir = direction;
		World.getWorld().schedule(new ScheduledTask(1, false) {
			@Override
			public void execute() {
				player.getBlockSet().add(
						SynchronizationBlock.createForceMovementBlock(player.getPosition(), new Position(x, y), speed1,
								speed2, dir));
				stop();
			}
		});
		// player.getUpdateFlags().sendAnimation(endAnim);
		World.getWorld().schedule(new ScheduledTask(time + 1, false) {
			@Override
			public void execute() {
				player.getSkillSet().addExperience(Skill.AGILITY, xp);
				player.teleport(new Position(endX, endY, endZ));
				player.getAppearance().setRunAnimation(-1);
				player.getAppearance().setWalkAnimation(-1);
				player.setAppearance(player.getAppearance());
				stop();
			}
		});
	}
}
