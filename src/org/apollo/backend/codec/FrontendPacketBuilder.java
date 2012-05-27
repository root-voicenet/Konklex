package org.apollo.backend.codec;

import java.util.List;
import java.util.Map.Entry;

import org.apollo.util.TextUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The builder for sending the {@link FrontendPacket}
 * @author Steve
 */
public final class FrontendPacketBuilder extends JSONObject {

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
	public void addParameter(String key, Object value) {
		try {
			super.put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a uri of parameters to the list.
	 * @param uri The uri of parameters.
	 */
	public void addUri(String uri) {
		for (Entry<String, List<Object>> kv : TextUtil.getUrlParameters(uri).entrySet()) {
			addParameter(kv.getKey(), kv.getValue().toString().replaceFirst("\\[", "").replaceFirst("\\]", ""));
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
		return new FrontendPacket(this, error);
	}
}
