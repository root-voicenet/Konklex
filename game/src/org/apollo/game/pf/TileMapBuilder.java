package org.apollo.game.pf;

import java.util.HashSet;
import java.util.Set;

import org.apollo.game.model.GameObject;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.region.Region;
import org.apollo.game.model.region.RegionManager;

/**
 * A class which assist in building <code>TileMap</code>s from a collection of <code>GameObject</code>s.
 * @author Graham Edgecombe
 */
public class TileMapBuilder {

	/**
	 * The tile map being built.
	 */
	private final TileMap tileMap;

	/**
	 * The center position.
	 */
	private final Position centerPosition;

	/**
	 * The radius.
	 */
	private final int radius;

	/**
	 * Sets up the tile map builder with the specified radius, and center position.
	 * @param position The center position.
	 * @param radius The radius.
	 */
	public TileMapBuilder(Position position, int radius) {
		this.centerPosition = position;
		this.tileMap = new TileMap(radius * 2 + 1, radius * 2 + 1);
		this.radius = radius;
	}

	/**
	 * Builds the tile map.
	 * @return The built tile map.
	 */
	public TileMap build() {
		// the region manager
		final RegionManager mgr = World.getWorld().getRegionManager();
		// a set of regions covered by our center position + radius
		final Set<Region> coveredRegions = new HashSet<Region>();

		// populates the set of covered regions
		for (int x = -radius - 1; x <= radius + 1; x++)
			for (int y = -radius - 1; y <= radius + 1; y++) {
				final Position loc = centerPosition.transform(x, y, 0);
				coveredRegions.add(mgr.getRegionByLocation(loc));
			}

		// calculate top left positions
		final int topX = centerPosition.getLocalX() - radius;
		final int topY = centerPosition.getY() - radius;

		// now fills in the tile map
		for (final Region region : coveredRegions)
			for (final GameObject obj : region.getGameObjects()) {

				if (!obj.getDefinition().isSolid())
					continue;
				final Position loc = obj.getLocation();
				if (loc.getHeight() != centerPosition.getHeight())
					continue;

				int sizeX = obj.getDefinition().getSizeX();
				int sizeY = obj.getDefinition().getSizeY();
				// position in the tile map
				final int posX = loc.getLocalX(centerPosition) - topX;
				final int posY = loc.getLocalY(centerPosition) - topY;
				if (obj.getRotation() == 1 || obj.getRotation() == 3) {
					// switch sizes if rotated
					final int temp = sizeX;
					sizeX = sizeY;
					sizeY = temp;
				}

				if (posX + sizeX < 0 || posY + sizeY < 0 || posX >= tileMap.getWidth() || posY >= tileMap.getHeight())
					continue;

				if (obj.getType() >= 0 && obj.getType() <= 3) {
					// walls
					if (posX >= 0 && posY >= 0 && posX < tileMap.getWidth() && posY < tileMap.getHeight()) {
						// int finalRotation = (obj.getType() +
						// obj.getRotation()) % 4;
						final int finalRotation = obj.getRotation();
						// finalRotation - 0 = west, 1 = north, 2 = east, 3 =
						// south
						final Tile t = tileMap.getTile(posX, posY);
						int flags = t.getTraversalMask();
						// clear flags
						if (finalRotation == 0)
							flags &= ~Tile.WEST_TRAVERSAL_PERMITTED;
						else if (finalRotation == 1)
							flags &= ~Tile.NORTH_TRAVERSAL_PERMITTED;
						else if (finalRotation == 2)
							flags &= ~Tile.EAST_TRAVERSAL_PERMITTED;
						else
							flags &= ~Tile.SOUTH_TRAVERSAL_PERMITTED;
						if (flags != t.getTraversalMask())
							tileMap.setTile(posX, posY, new Tile(flags));
					}
				}
				else if (obj.getType() == 9) {
					// diagonal walls
					if (posX >= 0 && posY >= 0 && posX < tileMap.getWidth() && posY < tileMap.getHeight())
						tileMap.setTile(posX, posY, TileMap.SOLID_TILE);
				}
				else if (obj.getType() == 10 || obj.getType() == 11)
					// world objects
					for (int offX = 0; offX <= sizeX; offX++)
						for (int offY = 0; offY <= sizeY; offY++) {
							final int x = offX + posX;
							final int y = offY + posY;
							if (x >= 0 && y >= 0 && x < tileMap.getWidth() && y < tileMap.getHeight())
								tileMap.setTile(x, y, TileMap.SOLID_TILE);
						}
				else if (obj.getType() == 22) {
					// floor decoration
					if (obj.getDefinition().getActions() != null)
						if (posX >= 0 && posY >= 0 && posX < tileMap.getWidth() && posY < tileMap.getHeight())
							tileMap.setTile(posX, posY, TileMap.SOLID_TILE);
				}
				else {
					// 4-8 are wall decorations and 12-21 are roofs
					// we can ignore those
				}
			}

		return tileMap;
	}

}