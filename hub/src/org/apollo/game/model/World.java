package org.apollo.game.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds a unique world.
 * @author Steve
 */
public final class World {

	/**
	 * The players.
	 */
	private final Map<String, Player> players = new HashMap<String, Player>();

	/**
	 * The time.
	 */
	private int time;

	/**
	 * The npcs.
	 */
	private int npcs;

	/**
	 * The objects.
	 */
	private int objects;

	/**
	 * The items.
	 */
	private int items;

	/**
	 * The regions.
	 */
	private int regions;

	/**
	 * The threads.
	 */
	private int threads;

	/**
	 * The cpu usage.
	 */
	private int cpu;

	/**
	 * The ram usage.
	 */
	private int ram;

	/**
	 * The status.
	 */
	private int status;

	/**
	 * Gets the players.
	 * @return The players.
	 */
	public Map<String, Player> getPlayers() {
		return players;
	}

	/**
	 * Checks if a player is online.
	 * @param user The username.
	 * @return True if online, false if otherwise.
	 */
	public boolean isPlayerOnline(String user) {
		return players.containsKey(user);
	}

	/**
	 * Gets the time.
	 * @return The time.
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Sets the time.
	 * @param time The time.
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Gets the npcs.
	 * @return The npcs.
	 */
	public int getNpcs() {
		return npcs;
	}

	/**
	 * Sets the npcs.
	 * @param npcs The npcs.
	 */
	public void setNpcs(int npcs) {
		this.npcs = npcs;
	}

	/**
	 * Gets the objects.
	 * @return The objects.
	 */
	public int getObjects() {
		return objects;
	}

	/**
	 * Sets the objects.
	 * @param objects The objects.
	 */
	public void setObjects(int objects) {
		this.objects = objects;
	}

	/**
	 * Gets the items.
	 * @return The items.
	 */
	public int getItems() {
		return items;
	}

	/**
	 * Sets the items.
	 * @param items The items.
	 */
	public void setItems(int items) {
		this.items = items;
	}

	/**
	 * Gets the regions.
	 * @return The regions.
	 */
	public int getRegions() {
		return regions;
	}

	/**
	 * Sets the regions.
	 * @param regions The regions.
	 */
	public void setRegions(int regions) {
		this.regions = regions;
	}

	/**
	 * Gets the threads.
	 * @return The threads.
	 */
	public int getThreads() {
		return threads;
	}

	/**
	 * Sets the threads.
	 * @param threads The threads.
	 */
	public void setThreads(int threads) {
		this.threads = threads;
	}

	/**
	 * Gets the cpu usage.
	 * @return The cpu usage.
	 */
	public int getCpu() {
		return cpu;
	}

	/**
	 * Sets the cpu usage.
	 * @param cpu The cpu usage.
	 */
	public void setCpu(int cpu) {
		this.cpu = cpu;
	}

	/**
	 * Gets the ram usage.
	 * @return The ram usage.
	 */
	public int getRam() {
		return ram;
	}

	/**
	 * Sets the ram usage.
	 * @param ram The ram usage.
	 */
	public void setRam(int ram) {
		this.ram = ram;
	}

	/**
	 * Gets the status.
	 * @return The status.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * @param status The status.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
