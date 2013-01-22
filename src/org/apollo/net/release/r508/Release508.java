package org.apollo.net.release.r508;

import org.apollo.net.meta.PacketMetaDataGroup;
import org.apollo.net.release.Release;

/**
 * An implementation of {@link Release} for the 508 protocol.
 * @author Steve
 */
public final class Release508 extends Release {
	
	/**
	 * The incoming packet lengths array.
	 */
	public static final int PACKET_LENGTHS[] = { -3, -3, -3, 8, -3, -3, -3, 2, -3, -3, //0-10
		-3, -3, -3, -3, -3, -3, -3, -3, -3, -3, //10
		-3, 6, 4, -3, -3, -3, -3, -3, -3, -3, //20
		-3, -3, -3, -3, -3, -3, -3, 2, 2, -3, //30
		-3, -3, -3, -3, -3, -3, -3, 0, -3, -1, //40
		-3, -3, 2, -3, -3, -3, -3, -3, -3, 6, //50
		0, -3, -3, -3, -3, -3, -3, -3, -3, -3, //60
		8, -3, -3, -3, -3, -3, -3, -3, -3, -3, //70
		-3, -3, -3, -3, 2, -3, -3, -3, 2, -3, //80
		-3, -3, -3, -3, -3, -3, -3, -3, -3, 4, //90
		-3, -3, -3, -3, -3, -3, -3, -1, 0, -3, //100
		-3, -3, -3, 4, -3, 0, -3, -1, -3, -1, //110
		-3, -3, -3, 2, -3, -3, -3, -3, -3, -3, //120
		-3, -3, -3, -3, -3, -3, -3, -3, -1, -3, //130
		-3, -3, -3, -3, -3, -3, -3, -3, -3, -3, //140
		-3, -3, -3, -3, -3, -3, -3, -3, 6, -3, //150
		2, -3, -3, -3, -3, 4, -3, 9, -3, 6, //160
		-3, -3, -3, -3, -3, -3, -3, -3, -3, 12, //170
		-3, -3, -3, -3, -3, -3, 8, -3, -3, -3, //180
		-3, -3, -3, -3, -3, -3, -3, -3, -3, -3, //190
		-3, 6, -3, 8, -3, -3, -3, -3, -3, -3, //200
		-3, 8, -3, -3, -3, -3, -3, -3, -3, -3, //210
		8, -3, -1, -3, -3, -3, -3, 2, 6, -3, //220
		-3, -3, 6, 6, -3, -3, -3, -3, -3, -3, //230
		-3, -3, -3, -3, -3, -3, -3, 4, 1, -3, //240
		-3, -3, -3, -3, -3, -3 //250
	};
	
	/**
	 * Creates and initializes this release.
	 */
	public Release508() {
		super(508, PacketMetaDataGroup.createFromArray(PACKET_LENGTHS));
		init();
	}
	
	/**
	 * Initializes this release by registering encoders and decoders.
	 */
	private void init() {
		register(new CommandEventDecoder(), 107);
	}
}
