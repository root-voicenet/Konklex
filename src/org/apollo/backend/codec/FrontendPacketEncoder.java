package org.apollo.backend.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * An encoder which encodes the {@link FrontendPacket}s.
 * @author Steve
 */
public final class FrontendPacketEncoder {

	/**
	 * The packet that is being encoded.
	 */
	private final FrontendPacket packet;

	/**
	 * Creates the frontend packet encoder.
	 * @param packet The fronend packet.
	 */
	public FrontendPacketEncoder(FrontendPacket packet) {
		this.packet = packet;
	}

	/**
	 * Encodes the packet structure.
	 * @return The encoded packet structure.
	 */
	public String encode() {
		Gson gson = new GsonBuilder().create();
		FrontendPacketReader reader = new FrontendPacketReader(packet);
		return gson.toJson(reader.getParameters());
	}
}
