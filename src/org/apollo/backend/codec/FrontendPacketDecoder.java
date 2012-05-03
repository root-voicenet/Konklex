package org.apollo.backend.codec;

import java.io.IOException;

/**
 * An encoder which encodes the {@link FrontendPacket}s.
 * @author Steve
 */
public final class FrontendPacketDecoder {

	/**
	 * The uri that was requested.
	 */
	private final String uri;

	/**
	 * Creates the frontend packet decoder.
	 * @param uri The uri that is being requested.
	 */
	public FrontendPacketDecoder(String uri) {
		this.uri = uri;
	}

	/**
	 * Decodes the uri into a packet structure.
	 * @return The packet structure.
	 */
	public FrontendPacket decode() {
		try {
			return new FrontendPacket(uri, false);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
