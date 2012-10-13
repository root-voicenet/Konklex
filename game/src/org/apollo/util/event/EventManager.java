package org.apollo.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages events which will be run in the future. Has its own thread since some events may need to be ran faster than
 * the cycle time in the main thread.
 * @author Graham
 */
public class EventManager implements Runnable {

	/** A reference to the singleton;. */
	private static EventManager singleton = null;

	/**
	 * The waitFor variable is multiplied by this before the call to wait() is made. We do this because other events may
	 * be executed after waitFor is set (and take time). We may need to modify this depending on event count? Some
	 * proper tests need to be done.
	 */
	private static final double WAIT_FOR_FACTOR = 0.5;

	/**
	 * Gets the event manager singleton. If there is no singleton, the singleton is created.
	 * @return The event manager singleton.
	 */
	public static EventManager getSingleton() {
		if (singleton == null) {
			singleton = new EventManager();
			singleton.thread = new Thread(singleton, "EventManager");
			singleton.thread.start();
		}
		return singleton;
	}

	/**
	 * Initialises the event manager (if it needs to be).
	 */
	public static void initialise() {
		getSingleton();
	}

	/**
	 * A list of events that are being executed.
	 */
	private final List<EventContainer> events;

	/**
	 * A list of events to add.
	 */
	private final List<EventContainer> eventsToAdd;

	/**
	 * The event manager thread. So we can interrupt it and end it nicely on shutdown.
	 */
	private Thread thread;

	/** Have we shutdown?. */
	private boolean isShutdown = false;

	/**
	 * Toggle shutdown.
	 */
	private boolean toggleShutdown = false;

	/**
	 * Initialise the event manager.
	 */
	private EventManager() {
		events = new ArrayList<EventContainer>();
		eventsToAdd = new ArrayList<EventContainer>();
	}

	/**
	 * Adds an event.
	 * @param event The event to add.
	 * @param tick The tick time.
	 */
	public void addEvent(Event event, int tick) {
		synchronized (eventsToAdd) {
			eventsToAdd.add(new EventContainer(event, tick));
		}
		synchronized (this) {
			notify();
		}
	}

	/**
	 * Gets the event count.
	 * @return The event count.
	 */
	public int getEventCount() {
		synchronized (events) {
			return events.size();
		}
	}

	/**
	 * Have we shutdown?.
	 * @return True if shutdown, false if not.
	 */
	public boolean isShutdown() {
		return isShutdown;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	/*
	 * Processes events. Works kinda like newer versions of cron.
	 */
	public void run() {
		long waitFor = -1;
		final List<EventContainer> remove = new ArrayList<EventContainer>();
		while (true) {
			if (toggleShutdown) {
				isShutdown = true;
				break;
			}
			synchronized (eventsToAdd) {
				for (final EventContainer c : eventsToAdd)
					events.add(c);
				eventsToAdd.clear();
			}
			waitFor = -1;
			for (final EventContainer container : events)
				if (container.isRunning()) {
					if (System.currentTimeMillis() - container.getLastRun() >= container.getTick())
						try {
							container.execute();
						}
						catch (final Exception e) {
						}
					if (container.getTick() < waitFor || waitFor == -1)
						waitFor = container.getTick();
				}
				else
					// add to remove list
					remove.add(container);
			for (final EventContainer container : remove)
				events.remove(container);
			remove.clear();
			try {
				if (waitFor == -1)
					synchronized (this) {
						wait();
					}
				else {
					final int decimalWaitFor = (int) Math.ceil(waitFor * WAIT_FOR_FACTOR);
					synchronized (this) {
						wait(decimalWaitFor);
					}
				}
			}
			catch (final InterruptedException e) {
				isShutdown = true;
				break; // stop running
			}
		}
	}

	/**
	 * Shuts the event manager down.
	 */
	public void shutdown() {
		thread.interrupt();
		toggleShutdown = true;
	}
}