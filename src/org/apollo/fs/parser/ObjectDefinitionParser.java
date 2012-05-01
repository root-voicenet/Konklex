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
		label0: do {
			int code;
			do {
				code = buffer.get() & 0xFF;
				if (code == 0) {
					return def;
				} else
					if (code == 1) {
						final int len = buffer.get();
						if (len > 0) {
							int[] objectTypes = new int[len];
							int[] modelIds = new int[len];
							for (int k1 = 0; k1 < len; k1++) {
								modelIds[k1] = buffer.getShort();
								objectTypes[k1] = buffer.get();
							}
						} else {
							buffer.position(len * 3);
						}
					} else
						if (code == 2) {
							def.setName(ByteBufferUtil.readString(buffer)); //name
						} else
							if (code == 3) {
								def.setDescription(ByteBufferUtil.readString(buffer)); //description
							} else
								if (code == 5) {
									final int len = buffer.get();
									if (len > 0) {
										int[] modelIds = new int[len];
										for (int k1 = 0; k1 < len; k1++) {
											modelIds[k1] = buffer.getShort();
										}
									} else {
										buffer.position(len * 2);
									}
								} else
									if (code == 14) {
										@SuppressWarnings("unused")
										int unknown = buffer.get(); //idk
									} else
										if (code == 15) {
											@SuppressWarnings("unused")
											int unknown = buffer.get(); //idk
										} else
											if (code == 17) {
												//idk
											} else
												if (code == 18) {
													//idk
												} else
													if (code == 19) {
														@SuppressWarnings("unused")
														int unknown = buffer.get();
													} else
														if (code == 21) {
															//idk
														} else
															if (code == 22) {
																//idk
															} else
																if (code == 23) {
																	//idk
																} else
																	if (code == 24) {
																		@SuppressWarnings("unused")
																		int unknown = buffer.getShort() & 0xFFFF;
																	} else
																		if (code == 28) {
																			@SuppressWarnings("unused")
																			int unknown = buffer.get();
																		} else
																			if (code == 29) {
																				@SuppressWarnings("unused")
																				int unknown = buffer.get();
																			} else
																				if (code == 39) {
																					@SuppressWarnings("unused")
																					int unknown = buffer.get();
																				} else
																					if (code >= 30 && code < 39) {
																						String description = ByteBufferUtil.readString(buffer);
																						def.addAction(code - 30, description);
																					} else
																						if (code == 40) {
																							int i1 = buffer.get();
																							for (int i2 = 0; i2 < i1; i2++) {
																								@SuppressWarnings("unused")
																								int unknown = buffer.getShort() & 0xFFFF;
																								@SuppressWarnings("unused")
																								int unknown2 = buffer.getShort() & 0xFFFF;
																							}
																						} else
																							if (code == 60) {
																								@SuppressWarnings("unused")
																								int unknown = buffer.getShort() & 0xFFFF;
																							} else
																								if (code == 62) {
																									//idk
																								} else
																									if (code == 64) {
																										//idk
																									} else
																										if (code == 65) {
																											int scaleX = buffer.getShort() & 0xFFFF;
																											def.setScaleX(scaleX);
																										} else
																											if (code == 66) {
																												int scaleY = buffer.getShort() & 0xFFFF;
																												def.setScaleY(scaleY);
																											} else
																												if (code == 67) {
																													int scaleZ = buffer.getShort() & 0xFFFF;
																													def.setScaleZ(scaleZ);
																												} else
																													if (code == 68) {
																														int mapSceneId = buffer.getShort() & 0xFFFF;
																														def.setMapSceneId(mapSceneId);
																													} else
																														if (code == 69) {
																															@SuppressWarnings("unused")
																															int unknown = buffer.get();
																														} else
																															if (code == 70) {
																																int offsetX = buffer.getShort() & 0xFFFF;
																																def.setOffsetX(offsetX);
																															} else
																																if (code == 71) {
																																	int offsetY = buffer.getShort() & 0xFFFF;
																																	def.setOffsetY(offsetY);
																																} else
																																	if (code == 72) {
																																		int offsetZ = buffer.getShort() & 0xFFFF;
																																		def.setOffsetZ(offsetZ);
																																	} else
																																		if (code == 73) {
																																			//idk
																																		} else
																																			if (code == 74) {
																																				//idk
																																			} else {
																																				if (code != 75) {
																																					continue;
																																				}
																																				@SuppressWarnings("unused")
																																				int unknown = buffer.get();
																																			}
				continue label0;
			} while (code != 77);
			@SuppressWarnings("unused")
			int unknown = buffer.getShort() & 0xFFFF;
			unknown = buffer.getShort() & 0xFFFF;
			final int j1 = buffer.get() & 0xFFFF;
			int[] childIds = new int[j1 + 1];
			for (int j2 = 0; j2 <= j1; j2++) {
				childIds[j2] = buffer.getShort() & 0xFFFF;
				if (childIds[j2] == 65535) {
					childIds[j2] = -1;
				}
			}
		} while (true);
	}
}