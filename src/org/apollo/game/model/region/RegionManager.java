package org.apollo.game.model.region;

import java.util.HashMap;
import java.util.Map;

import org.apollo.game.model.Position;

/**
 * A world region manager.
 * @author Solid Snake
 */
public class RegionManager {

	/**
	 * A map of the regions.
	 */
	private Map<RegionCoordinates, Region> activeRegions = new HashMap<RegionCoordinates, Region>();

	/**
	 * Gets the region by the x and y coordinates.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The region.
	 */
	public Region getRegion(int x, int y) {
		RegionCoordinates key = new RegionCoordinates(x, y);
		if (activeRegions.containsKey(key)) {
			return activeRegions.get(key);
		} else {
			Region region = new Region(key);
			activeRegions.put(key, region);
			return region;
		}
	}

	/**
	 * Gets the region by the position.
	 * @param position The position.
	 * @return The region.
	 */
	public Region getRegionByLocation(Position position) {
		return getRegion(position.getX() / Region.REGION_SIZE, position.getY() / Region.REGION_SIZE);
	}
}
