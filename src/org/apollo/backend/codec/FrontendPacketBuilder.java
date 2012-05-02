package org.apollo.backend.codec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apollo.util.TextUtil;

/**
 * The builder for sending the {@link FrontendPacket}
 * @author Steve
 */
public final class FrontendPacketBuilder {

	/**
	 * The parameters requested or being sent.
	 */
	private Map<String, List<String>> parameters = new HashMap<String, List<String>>();

	/**
	 * The error flag.
	 */
	private boolean error = false;

	/**
	 * Creates the frontend packet builder.
	 * @param method The method.
	 */
	public FrontendPacketBuilder(String method) {
		addParameter("method", method);
	}

	/**
	 * Adds a parameter to the list.
	 * @param key The key.
	 * @param value The value.
	 */
	public void addParameter(String key, String value) {
		ArrayList<String> values = new ArrayList<String>();
		values.add(value);
		parameters.put(key, values);
	}

	/**
	 * Adds parameters to the list.
	 * @param key The key.
	 * @param values The values.
	 */
	public void addParameters(String key, ArrayList<String> values) {
		parameters.put(key, values);
	}

	/**
	 * Adds a uri of parameters to the list.
	 * @param uri The uri of parameters.
	 */
	public void addUri(String uri) {
		try {
			parameters.putAll(TextUtil.getUrlParameters(uri));
		} catch (Exception e) {
		}
	}

	/**
	 * Sets the error flag.
	 * @param error The error flag.
	 */
	public void setError(boolean error) {
		this.error = error;
	}

	/**
	 * Encodes the opcode to a frontend packet.
	 * @return The completed frontend packet.
	 */
	public FrontendPacket toFrontendPacket() {
		return new FrontendPacket(parameters, error);
	}
}
