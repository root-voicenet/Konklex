package org.apollo.backend.codec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The reader for the {@link FrontendPacket}
 * @author Steve
 */
public final class FrontendPacketReader {

	/**
	 * The packet that is used for reading.
	 */
	private final FrontendPacket packet;

	/**
	 * The readable map.
	 */
	private final Map<String, String> parameters;

	/**
	 * Reads the frontend packet.
	 * @param packet The frontend packet.
	 */
	public FrontendPacketReader(FrontendPacket packet) {
		this.packet = packet;
		this.parameters = getReadableParameters();
	}

	/**
	 * Gets the parameter.
	 * @param parameter The parameter.
	 * @return The paramter's value.
	 */
	public String getParameter(String parameter) {
		return parameters.get(parameter);
	}

	/**
	 * Gets the whole list of parameters.
	 * @return The list of parameters.
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Transforms the parameters to a easier map.
	 * @return The parameters.
	 */
	private Map<String, String> getReadableParameters() {
		Map<String, String> returnz = new HashMap<String, String>();
		for (Entry<String, List<String>> kv : packet.getParameters().entrySet()) {
			returnz.put(kv.getKey(), kv.getValue().toString().replaceFirst("\\[", "").replaceFirst("\\]", ""));
		}
		return returnz;
	}
}
