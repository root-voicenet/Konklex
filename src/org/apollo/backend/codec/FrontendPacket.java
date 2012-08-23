package org.apollo.backend.codec;

import org.json.JSONObject;

/**
 * The default packet for sending the codec information to the frontend.
 * @author Steve
 */
public final class FrontendPacket {

	/**
	 * The parameters requested or being sent.
	 */
	private final JSONObject json;

	/**
	 * The error flag.
	 */
	private final boolean error;

	/**
	 * Creates a new frontend packet.
	 * @param json The parameters.
	 * @param error The error flag.
	 */
	public FrontendPacket(JSONObject json, boolean error) {
		this.json = json;
		this.error = error;
	}

	/**
	 * Gets the whole list of parameters.
	 * @return The list of parameters.
	 */
	public JSONObject getParameters() {
		return json;
	}

	/**
	 * Gets the error flag.
	 * @return True if error, false if not.
	 */
	public boolean isError() {
		return error;
	}

	@Override
	public String toString() {
		return json.toString();
	}
}
