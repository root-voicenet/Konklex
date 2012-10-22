package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apollo.fs.IndexedFileSystem;
import org.apollo.fs.archive.Archive;
import org.apollo.game.model.Position;
import org.apollo.game.model.World;
import org.apollo.game.model.obj.StaticObject;
import org.apollo.util.ByteBufferUtil;
import org.apollo.util.CompressionUtil;

/**
 * A class which parses the placement information of in-game objects on the map.
 * @author Chris Fletcher
 */
public final class StaticObjectDefinitionParser {

	/**
	 * The indexed file system.
	 */
	private final IndexedFileSystem fs;

	/**
	 * Creates a new static object parser.
	 * @param fs The indexed file system.
	 */
	public StaticObjectDefinitionParser(IndexedFileSystem fs) {
		this.fs = fs;
	}

	/**
	 * Parses all static objects and places them in the returned array.
	 * @return The parsed objects.
	 * @throws IOException if an I/O error occurs.
	 */
	public StaticObject[] parse() throws IOException {
		Archive versionList = Archive.decode(fs.getFile(0, 5));
		ByteBuffer buffer = versionList.getEntry("map_index").getBuffer();

		int indices = buffer.remaining() / 7;
		int[] areas = new int[indices];
		int[] landscapes = new int[indices];

		for (int i = 0; i < indices; i++) {
			areas[i] = buffer.getShort() & 0xFFFF;

			@SuppressWarnings("unused")
			int mapFile = buffer.getShort() & 0xFFFF;

			landscapes[i] = buffer.getShort() & 0xFFFF;

			@SuppressWarnings("unused")
			boolean members = (buffer.get() & 0xFF) == 1;
		}

		List<StaticObject> objects = new ArrayList<StaticObject>();

		for (int i = 0; i < indices; i++) {
			ByteBuffer compressed = fs.getFile(4, landscapes[i]);
			ByteBuffer uncompressed = ByteBuffer.wrap(CompressionUtil.ungzip(compressed));

			Collection<StaticObject> areaObjects = parseArea(areas[i], uncompressed);
			objects.addAll(areaObjects);
		}

		return objects.toArray(new StaticObject[objects.size()]);
	}

	/**
	 * Parses a single area from the specified buffer.
	 * @param area The identifier of that area.
	 * @param buffer The buffer which holds the area's data.
	 * @return A collection of all parsed objects.
	 */
	private Collection<StaticObject> parseArea(int area, ByteBuffer buffer) {
		List<StaticObject> objects = new ArrayList<StaticObject>();

		int x = (area >> 8 & 0xFF) * 64;
		int y = (area & 0xFF) * 64;

		int id = -1;
		int idOffset;

		while ((idOffset = ByteBufferUtil.readSmart(buffer)) != 0) {
			id += idOffset;

			int position = 0;
			int positionOffset;

			while ((positionOffset = ByteBufferUtil.readSmart(buffer)) != 0) {
				position += positionOffset - 1;

				int localX = position >> 6 & 0x3F;
				int localY = position & 0x3F;
				int height = position >> 12;

				int info = buffer.get() & 0xFF;
				int type = info >> 2;
				int rotation = info & 3;

				Position pos = new Position(x + localX, y + localY, height);

				StaticObject object = new StaticObject(id, pos, type, rotation);
				objects.add(object);

				World.getWorld().getRegionManager().getRegionByLocation(pos).addStaticObject(object);
			}
		}

		return objects;
	}

}