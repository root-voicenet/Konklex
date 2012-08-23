package org.apollo.game.scheduling.impl;

import org.apollo.game.model.Npc;
import org.apollo.game.model.Position;
import org.apollo.game.model.WalkingQueue;
import org.apollo.game.scheduling.ScheduledTask;

/**
 * An {@link ScheduledTask} which makes a npc walk randomly.
 * @author Steve
 */
public final class RandomizedNpcWalkingTask extends ScheduledTask {

    /**
     * The npc.
     */
    private final Npc npc;

    /**
     * Creates the randomized task.
     * @param npc The npc.
     */
    public RandomizedNpcWalkingTask(Npc npc) {
	super(20, true);
	this.npc = npc;
    }

    @Override
    public void execute() {
	if (!npc.isRandomWalking())
	    return;
	// TODO add certain npcs for random walking
	// TODO add npc clipping
	int moveX = random(1);
	int moveY = random(1);
	final int rnd = random(4);
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
	}
	final Position position = new Position(npc.getPosition().getX() + moveX, npc.getPosition().getY() + moveY);
	final WalkingQueue queue = npc.getWalkingQueue();
	queue.addStep(position);
    }

    /**
     * Gets a random integer.
     * @param range The range.
     * @return The random integer.
     */
    private int random(int range) {
	return (int) (Math.random() * (range + 1));
    }

}
