package org.apollo.game.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

import org.apollo.game.event.impl.UpdateRunEnergyEvent;

/**
 * A queue of {@link Direction}s which a {@link Character} will follow.
 * @author Graham
 */
public final class WalkingQueue {

    /**
     * Represents a single point in the queue.
     * @author Graham
     */
    private static final class Point {

	/**
	 * The point's position.
	 */
	private final Position position;

	/**
	 * The direction to walk to this point.
	 */
	private final Direction direction;

	/**
	 * Creates a point.
	 * @param position The position.
	 * @param direction The direction.
	 */
	public Point(Position position, Direction direction) {
	    this.position = position;
	    this.direction = direction;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
	    return Point.class.getName() + " [direction=" + direction + ", position=" + position + "]";
	}
    }

    /**
     * The maximum size of the queue. If any additional steps are added, they
     * are discarded.
     */
    private static final int MAXIMUM_SIZE = 128;

    /**
     * The character whose walking queue this is.
     */
    private final Character character;

    /**
     * The queue of directions.
     */
    private final Deque<Point> points = new ArrayDeque<Point>();

    /**
     * The old queue of directions.
     */
    private final Deque<Point> oldPoints = new ArrayDeque<Point>();

    /**
     * Flag indicating if this queue (only) should be ran.
     */
    private boolean runningQueue;

    /**
     * Flag indicating if this player wants to run.
     */
    private boolean running;

    /**
     * Creates a walking queue for the specified character.
     * @param character The character.
     */
    public WalkingQueue(Character character) {
	this.character = character;
    }

    /**
     * Adds the first step to the queue, attempting to connect the server and
     * client position by looking at the previous queue.
     * @param clientConnectionPosition The first step.
     * @return {@code true} if the queues could be connected correctly,
     * {@code false} if not.
     */
    public boolean addFirstStep(Position clientConnectionPosition) {
	final Position serverPosition = character.getPosition();
	int deltaX = clientConnectionPosition.getX() - serverPosition.getX();
	int deltaY = clientConnectionPosition.getY() - serverPosition.getY();
	if (Direction.isConnectable(deltaX, deltaY)) {
	    points.clear();
	    oldPoints.clear();
	    addStep(clientConnectionPosition);
	    return true;
	}
	final Queue<Position> travelBackQueue = new ArrayDeque<Position>();
	Point oldPoint;
	while ((oldPoint = oldPoints.pollLast()) != null) {
	    final Position oldPosition = oldPoint.position;
	    deltaX = oldPosition.getX() - serverPosition.getX();
	    deltaY = oldPosition.getX() - serverPosition.getY();
	    travelBackQueue.add(oldPosition);
	    if (Direction.isConnectable(deltaX, deltaY)) {
		points.clear();
		oldPoints.clear();
		for (final Position travelBackPosition : travelBackQueue)
		    addStep(travelBackPosition);
		addStep(clientConnectionPosition);
		return true;
	    }
	}
	oldPoints.clear();
	return false;
    }

    /**
     * Adds a step.
     * @param x The x coordinate of this step.
     * @param y The y coordinate of this step.
     */
    private void addStep(int x, int y) {
	if (points.size() >= MAXIMUM_SIZE)
	    return;
	final Point last = getLast();
	final int deltaX = x - last.position.getX();
	final int deltaY = y - last.position.getY();
	final Direction direction = Direction.fromDeltas(deltaX, deltaY);
	if (direction != Direction.NONE) {
	    final Point p = new Point(new Position(x, y, last.position.getHeight()), direction);
	    points.add(p);
	    oldPoints.add(p);
	}
    }

    /**
     * Adds a step to the queue.
     * @param step The step to add.
     */
    public void addStep(Position step) {
	final Point last = getLast();
	final int x = step.getX();
	final int y = step.getY();
	int deltaX = x - last.position.getX();
	int deltaY = y - last.position.getY();
	final int max = Math.max(Math.abs(deltaX), Math.abs(deltaY));
	for (int i = 0; i < max; i++) {
	    if (deltaX < 0)
		deltaX++;
	    else if (deltaX > 0)
		deltaX--;
	    if (deltaY < 0)
		deltaY++;
	    else if (deltaY > 0)
		deltaY--;
	    addStep(x - deltaX, y - deltaY);
	}
    }

    /**
     * Clears the walking queue.
     */
    public void clear() {
	points.clear();
	oldPoints.clear();
    }

    /**
     * Gets the last point.
     * @return The last point.
     */
    private Point getLast() {
	final Point last = points.peekLast();
	if (last == null)
	    return new Point(character.getPosition(), Direction.NONE);
	return last;
    }

    /**
     * Gets the running flag.
     * @return True if running, false if not.
     */
    public boolean getRunning() {
	return running;
    }

    /**
     * Gets the running queue flag.
     * @return True if running queue, false if not.
     */
    public boolean getRunningQueue() {
	return runningQueue;
    }

    /**
     * Called every pulse, updates the queue.
     */
    public void pulse() {
	Position position = character.getPosition();
	Direction first = Direction.NONE;
	Direction second = Direction.NONE;
	Point next = points.poll();
	if (next != null) {
	    first = next.direction;
	    position = next.position;
	    if (character.getRunEnergy() >= 1) {
		if (running) {
		    if (character instanceof Player) {
			final Player player = (Player) character;
			player.setRunEnergy(player.getRunEnergy() - 1);
			player.send(new UpdateRunEnergyEvent(player.getRunEnergy()));
		    }
		    next = points.poll();
		    if (next != null) {
			second = next.direction;
			position = next.position;
		    }
		    setRunningQueue(true);
		} else
		    setRunningQueue(false);
	    } else {
		setRunningQueue(false);
		setRunning(false);
	    }
	}
	character.setDirections(first, second);
	character.setPosition(position);
    }

    /**
     * Sets the running queue flag.
     * @param running The running queue flag.
     */
    public void setRunning(boolean running) {
	this.running = running;
    }

    /**
     * Sets the running queue flag.
     * @param running The running queue flag.
     */
    public void setRunningQueue(boolean running) {
	this.runningQueue = running;
    }

    /**
     * Gets the size of the queue.
     * @return The size of the queue.
     */
    public int size() {
	return points.size();
    }
}
