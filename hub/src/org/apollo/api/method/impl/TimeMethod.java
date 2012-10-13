package org.apollo.api.method.impl;

import org.apollo.api.method.Method;

/**
 * An {@link Method} that sends the time to the login servers.
 * @author Steve
 */
public final class TimeMethod extends Method {
	
	/**
	 * The time.
	 */
	private final long time;
	
	/**
	 * The world.
	 */
	private final int world;

	/**
	 * The objects.
	 */
	private final int objects;

	/**
	 * The npcs.
	 */
	private final int npcs;

	/**
	 * The items.
	 */
	private final int items;

	/**
	 * The regions.
	 */
	private final int regions;

	/**
	 * The threads.
	 */
	private final int threads;

	/**
	 * The cpu usage.
	 */
	private final int cpu;

	/**
	 * The ram usage.
	 */
	private final int ram;

	/**
	 * The status.
	 */
	private final int status;
	
	/**
	 * Creates the time method.
	 * @param world The world id.
	 * @param objects The objects.
	 * @param npcs The npcs.
	 * @param items The items.
	 * @param regions The regions.
	 * @param time The time.
	 * @param threads The threads.
	 * @param cpu The cpu usage.
	 * @param ram The ram usage.
	 * @param status The status.
	 */
	public TimeMethod(int world, int objects, int npcs, int items, int regions, long time, int threads, int cpu, int ram, int status) {
		this.world = world;
		this.objects = objects;
		this.npcs = npcs;
		this.items = items;
		this.regions = regions;
		this.time = time;
		this.threads = threads;
		this.cpu = cpu;
		this.ram = ram;
		this.status = status;
	}
	
	/**
	 * Gets the time.
	 * @return The time.
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Gets the world.
	 * @return The world.
	 */
	public int getWorld() {
		return world;
	}

	/**
	 * Gets the objects.
	 * @return The objects.
	 */
	public int getObjects() {
		return objects;
	}

	/**
	 * Gets the npcs.
	 * @return The npcs.
	 */
	public int getNpcs() {
		return npcs;
	}

	/**
	 * Gets the items.
	 * @return The items.
	 */
	public int getItems() {
		return items;
	}

	/**
	 * Gets the regions.
	 * @return The regions.
	 */
	public int getRegions() {
		return regions;
	}

	/**
	 * Gets the threads.
	 * @return The threads.
	 */
	public int getThreads() {
		return threads;
	}

	/**
	 * Gets the cpu usage.
	 * @return The cpu usage.
	 */
	public int getCpu() {
		return cpu;
	}

	/**
	 * Gets the ram usage.
	 * @return The ram usage.
	 */
	public int getRam() {
		return ram;
	}

	/**
	 * Gets the status.
	 * @return The status.
	 */
	public int getStatus() {
		return status;
	}

}
