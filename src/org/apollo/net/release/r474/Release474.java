package org.apollo.net.release.r474;

import org.apollo.net.meta.PacketMetaDataGroup;
import org.apollo.net.release.Release;

/**
 * An implementation of {@link Release} for the 474 protocol.
 * @author Steve
 */
public final class Release474 extends Release {

	/**
	 * The incoming packet lengths array.
	 */
	public static final int[] PACKET_LENGTHS = { 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, // 0-9
		0, 1, 0, 6, 0, 4, 4, 0, 0, 0, // 10-19
		0, 0, 0, -2, 0, 5, 0, 0, 0, 0, // 20-29
		-2, 0, 0, 8, 0, 0, 6, 0, 0, 6, // 30-39
		0, 3, 0, -2, 0, 0, 0, 0, 0, 0, // 40-49
		4, 0, 0, 11, 0, -2, 0, 0, 10, 0, // 50-59
		-1, 0, -1, 0, 0, 0, 0, 0, 0, 0, // 60-69
		-2, 10, 0, -2, 0, 0, 4, 0, 7, 0, // 70-79
		0, 0, 0, 0, 0, 0, 5, 0, 0, 0, // 80-89
		6, 0, 0, 0, 0, 2, 0, 0, 0, 0, // 90-99
		15, 2, 3, 0, 1, 0, 0, 0, 0, 0, // 100-109
		0, 0, 0, 0, 0, 5, 0, 0, 0, 6, // 110-119
		2, -2, 0, 0, 0, 0, -2, 0, 3, 6, // 120-129
		0, 0, 2, -1, 0, -1, 0, 0, 0, 0, // 130-139
		0, 7, 0, 0, 5, 0, 0, 6, 0, 6, // 140-149
		0, 3, 0, 0, 0, 8, 0, 0, 12, 0, // 150-159
		0, 0, 0, 0, 7, 0, 0, 0, 0, 4, // 160-169
		4, 0, 0, 6, 0, 0, 0, 6, 0, 0, // 170-179
		0, 0, 0, 0, 0, 0, 6, 0, 0, 0, // 180-189
		0, 0, 0, 0, 0, -2, 0, 1, 0, 0, // 190-199
		-2, 0, 0, 0, 0, 0, 0, 0, 0, 4, // 200-209
		2, 0, 0, 0, 0, 0, 0, 0, -2, -2, // 210-219
		-1, 0, 0, 0, 5, 2, 0, 0, 0, 0, // 220-229
		0, 0, 0, 2, 0, 0, 0, 0, 0, 2, // 230-239
		6, 0, 11, 0, 0, 14, 0, 0, 0, 0, // 240-249
		0, 0, 0, 0, 0, 0 // 250-255
	};

	/**
	 * Creates and initializes this release.
	 */
	public Release474() {
		super(474, PacketMetaDataGroup.createFromArray(PACKET_LENGTHS));
		init();
	}

	/**
	 * Initializes this release by registering encoders and decoders.
	 */
	private void init() {
		// register decoders
		register(new ClientFocusChangeEventDecoder(), 34);
		register(new ButtonEventDecoder(), 215);
	}
}
