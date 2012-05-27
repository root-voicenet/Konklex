package org.apollo.game.scheduling.impl;

import java.util.Random;

import org.apollo.game.model.Npc;
import org.apollo.game.model.Position;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} which makes a npc walk randomly.
 * @author eMachines
 */
public final class RandomizedNpcWalkingTask extends ScheduledTask {

    /**
     * The npc.
     */
    private final Npc npc;

    /**
     * The random integer generator.
     */
    private final Random random = new Random();

    /**
     * Creates the randomized task.
     * @param npc The npc.
     */
    public RandomizedNpcWalkingTask(Npc npc) {
	super(5, true);
	this.npc = npc;
    }

    @Override
    public void execute() {
	// TODO add certain npcs for random walking
	// TODO add npc clipping
	if (random.nextInt(8) == 1) {
	    int moveX = random.nextInt(1);
	    int moveY = random.nextInt(1);
	    final int rnd = random.nextInt(4);
	    switch (rnd) {
	    case 1:
		moveX = -moveX;
		moveY = -moveY;
		break;
	    case 2:
		moveX = -moveX;
		break;
	    case 3:
		moveY = -moveY;
		break;
	    default:
		break;
	    }
	    npc.getWalkingQueue().addStep(
		    new Position(npc.getPosition().getX() + moveX, npc.getPosition().getY() + moveY));
	}
    }

}
