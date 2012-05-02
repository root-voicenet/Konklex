package org.apollo.backend.codec;

/**
 * The reader for the {@link FrontendPacket}
 * @author Steve
 */
public final class FrontendPacketReader {

	/**
	 * The packet that is used for reading.
	 */
	@SuppressWarnings("unused")
	private final FrontendPacket packet;

	/**
	 * Reads the frontend packet.
	 * @param packet The frontend packet.
	 */
	public FrontendPacketReader(FrontendPacket packet) {
		this.packet = packet;
	}
}
