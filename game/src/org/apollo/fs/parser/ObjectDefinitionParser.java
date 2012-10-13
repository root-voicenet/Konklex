package org.apollo.fs.parser;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apollo.fs.IndexedFileSystem;
import org.apollo.fs.archive.Archive;
import org.apollo.game.model.def.ObjectDefinition;
import org.apollo.util.ByteBufferUtil;

/**
 * A class which parses object definitions.
 * @author Steve
 */
public final class ObjectDefinitionParser {

	/**
	 * The indexed file system.
	 */
	private final IndexedFileSystem fs;

	/**
	 * Creates the object definition parser.
	 * @param fs The indexed file system.
	 */
	public ObjectDefinitionParser(IndexedFileSystem fs) {
		this.fs = fs;
	}

	/**
	 * Parses the object definitions.
	 * @return The object definitions.
	 * @throws IOException if an I/O error occurs.
	 */
	public ObjectDefinition[] parse() throws IOException {
		Archive config = Archive.decode(fs.getFile(0, 2));
		ByteBuffer dat = config.getEntry("loc.dat").getBuffer();
		ByteBuffer idx = config.getEntry("loc.idx").getBuffer();
		int count = idx.getShort() & 0xFFFF;
		int[] indices = new int[count];
		int index = 2;
		for (int i = 0; i < count; i++) {
			indices[i] = index;
			index += idx.getShort();
		}
		ObjectDefinition[] defs = new ObjectDefinition[count];
		for (int i = 0; i < count; i++) {
			dat.position(indices[i]);
			defs[i] = parseDefinition(i, dat);
		}
		return defs;
	}

	/**
	 * Parses a single definition.
	 * @param id The object's id.
	 * @param buffer The buffer.
	 * @return The definition.
	 */
	private ObjectDefinition parseDefinition(int id, ByteBuffer buffer) {
		ObjectDefinition def = new ObjectDefinition(id);

		int interactableValue = -1;
		boolean walkableBlock = false;
		int[] modelIds = null, objectTypes = null;

		outer_loop: do {
			int code;
			do {
				code = buffer.get() & 0xFF;
				if (code == 0) {
					break outer_loop;
				}
				else if (code == 1) {
					final int len = buffer.get();
					if (len > 0) {
						modelIds = new int[len];
						objectTypes = new int[len];
						for (int k1 = 0; k1 < len; k1++) {
							modelIds[k1] = buffer.getShort();
							objectTypes[k1] = buffer.get();
						}
					}
					else {
						buffer.position(buffer.position() + (len * 3));
					}
				}
				else if (code == 2) {
					def.setName(ByteBufferUtil.readString(buffer));
				}
				else if (code == 3) {
					def.setDescription(ByteBufferUtil.readString(buffer));
				}
				else if (code == 5) {
					final int len = buffer.get();
					if (len > 0) {
						modelIds = new int[len];
						for (int k1 = 0; k1 < len; k1++) {
							modelIds[k1] = buffer.getShort();
						}
					}
					else {
						buffer.position(buffer.position() + (len * 3));
					}
				}
				else if (code == 14) {
					int sizeX = buffer.get() & 0xFF;
					def.setSizeX(sizeX);
				}
				else if (code == 15) {
					int sizeY = buffer.get() & 0xFF;
					def.setSizeY(sizeY);
				}
				else if (code == 17) {
					def.setWalkable(false);
				}
				else if (code == 18) {
					def.setSolid(false);
				}
				else if (code == 19) {
					interactableValue = buffer.get() & 0xFF;
					def.setInteractable(interactableValue == 1);
				}
				else if (code == 21) {
					@SuppressWarnings("unused")
					boolean flatTerrain = true;
				}
				else if (code == 22) {
					@SuppressWarnings("unused")
					boolean flatShading = true;
				}
				else if (code == 23) {
					@SuppressWarnings("unused")
					boolean unknown = true;
				}
				else if (code == 24) {
					@SuppressWarnings("unused")
					int unknown = buffer.getShort() & 0xFFFF;
				}
				else if (code == 28) {
					@SuppressWarnings("unused")
					int unknown = buffer.get();
				}
				else if (code == 29) {
					@SuppressWarnings("unused")
					int unknown = buffer.get();
				}
				else if (code == 39) {
					@SuppressWarnings("unused")
					int unknown = buffer.get();
				}
				else if (code >= 30 && code < 39) {
					String action = ByteBufferUtil.readString(buffer);
					def.addAction(code - 30, action);
				}
				else if (code == 40) {
					int i1 = buffer.get();
					for (int i2 = 0; i2 < i1; i2++) {
						@SuppressWarnings("unused")
						int unknown = buffer.getShort() & 0xFFFF;
						@SuppressWarnings("unused")
						int unknown2 = buffer.getShort() & 0xFFFF;
					}
				}
				else if (code == 60) {
					@SuppressWarnings("unused")
					int unknown = buffer.getShort() & 0xFFFF;
				}
				else if (code == 62) {
					// idk
				}
				else if (code == 64) {
					// idk
				}
				else if (code == 65) {
					int scaleX = buffer.getShort() & 0xFFFF;
					def.setScaleX(scaleX);
				}
				else if (code == 66) {
					int scaleY = buffer.getShort() & 0xFFFF;
					def.setScaleY(scaleY);
				}
				else if (code == 67) {
					int scaleZ = buffer.getShort() & 0xFFFF;
					def.setScaleZ(scaleZ);
				}
				else if (code == 68) {
					int mapSceneId = buffer.getShort() & 0xFFFF;
					def.setMapSceneId(mapSceneId);
				}
				else if (code == 69) {
					@SuppressWarnings("unused")
					int unknown = buffer.get();
				}
				else if (code == 70) {
					int offsetX = buffer.getShort() & 0xFFFF;
					def.setOffsetX(offsetX);
				}
				else if (code == 71) {
					int offsetY = buffer.getShort() & 0xFFFF;
					def.setOffsetY(offsetY);
				}
				else if (code == 72) {
					int offsetZ = buffer.getShort() & 0xFFFF;
					def.setOffsetZ(offsetZ);
				}
				else if (code == 73) {
					// idk
				}
				else if (code == 74) {
					// idk
				}
				else {
					if (code != 75) {
						continue;
					}
					@SuppressWarnings("unused")
					int unknown = buffer.get();
				}
				continue outer_loop;
			} while (code != 77);

			int unknown1 = buffer.getShort(); // Initial = -1
			if (unknown1 == 65535) {
				unknown1 = -1;
			}
			int unknown2 = buffer.getShort(); // Initial = -1
			if (unknown2 == 65535) {
				unknown2 = -1;
			}

			int count = buffer.get() & 0xFF;
			int[] children = new int[count + 1]; // Initial = null
			for (int i = 0; i <= count; i++) {
				int child = buffer.getShort();
				if (child == 65535) {
					child = -1;
				}
				children[i] = child;
			}
		} while (true);

		if (interactableValue == -1) {
			boolean interactable = modelIds != null && (objectTypes == null || objectTypes[0] == 10);
			def.setInteractable(interactable);

			if (!interactable) { // Check to prevent unnecessary loop.
				for (String str : def.getActions()) {
					if (str != null) {
						def.setInteractable(true);
						break;
					}
				}
			}
		}

		if (walkableBlock) {
			def.setWalkable(false);
			def.setSolid(false);
		}

		return def;
	}
}