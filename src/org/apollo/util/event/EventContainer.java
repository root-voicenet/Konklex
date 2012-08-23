package org.apollo.util.event;

/**
 * Holds extra data for an event (for example the tick time etc).
 * @author Graham
 */
public class EventContainer {

    /**
     * The tick time in milliseconds.
     */
    private final int tick;

    /**
     * The actual event.
     */
    private final Event event;

    /** A flag which specifies if the event is running;. */
    private boolean isRunning;

    /**
     * When this event was last run.
     */
    private long lastRun;

    /**
     * The event container.
     * @param event2 the event2
     * @param tick the tick
     */
    protected EventContainer(Event event2, int tick) {
	this.tick = tick;
	event = event2;
	isRunning = true;
	lastRun = System.currentTimeMillis();
	// can be changed to 0 if you want events to run straight away
    }

    /**
     * Executes the event!.
     */
    public void execute() {
	lastRun = System.currentTimeMillis();
	event.execute(this);
    }

    /**
     * Gets the last run time.
     * @return The last run time.
     */
    public long getLastRun() {
	return lastRun;
    }

    /**
     * Returns the tick time.
     * @return The tick time.
     */
    public int getTick() {
	return tick;
    }

    /**
     * Returns the is running flag.
     * @return True if running, false if not.
     */
    public boolean isRunning() {
	return isRunning;
    }

    /**
     * Stops this event.
     */
    public void stop() {
	isRunning = false;
    }
}