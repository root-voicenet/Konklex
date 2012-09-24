package org.apollo.net.codec.api;

/**
 * The api request.
 * @author Steve
 */
public final class ApiRequest {
	
	/**
	 * The nodes.
	 */
	private final int nodes;
	
	/**
	 * The worlds.
	 */
	private final int[][] worlds;
	
	/**
	 * Creates a api request.
	 * @param nodes The nodes.
	 * @param worlds The worlds.
	 */
	public ApiRequest(int nodes, int[][] worlds) {
		this.nodes = nodes;
		this.worlds = worlds;
	}
	
	/**
	 * Gets the worlds.
	 * @return The worlds.
	 */
	public int[][] getWorlds() {
		return worlds;
	}
	
	/**
	 * Gets the nodes.
	 * @return The nodes.
	 */
	public int getNodes() {
		return nodes;
	}

}
