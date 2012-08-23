package org.apollo.backend.codec;

import org.json.JSONException;

/**
 * The reader for the {@link FrontendPacket}
 * 
 * @author Steve
 */
public final class FrontendPacketReader {

	/**
	 * The packet that is used for reading.
	 */
	private final FrontendPacket packet;

	/**
	 * Reads the frontend packet.
	 * 
	 * @param packet
	 *            The frontend packet.
	 */
	public FrontendPacketReader(FrontendPacket packet) {
		this.packet = packet;
	}

	/**
	 * Gets a boolean.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @return The boolean.
	 */
	public boolean getBoolean(String parameter) {
		return Boolean.parseBoolean((String) getParameter(parameter));
	}

	/**
	 * Gets an integer.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @return The integer.
	 */
	public int getInt(String parameter) {
		return Integer.parseInt((String) getParameter(parameter));
	}

	/**
	 * Gets a long.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @return The long.
	 */
	public long getLong(String parameter) {
		return Long.parseLong((String) getParameter(parameter));
	}

	/**
	 * Gets the requested method.
	 * 
	 * @return The requested method.
	 */
	public String getMethod() {
		return getParameter("method");
	}

	/**
	 * Gets the parameter.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @return The paramter's value.
	 */
	@SuppressWarnings("unchecked")
	private <E extends Object> E getParameter(String parameter) {
		E returnz = null;
		try {
			returnz = (E) packet.getParameters().get(parameter);
		} catch (final JSONException e) {
		}
		return returnz;
	}

	/**
	 * Gets a string.
	 * 
	 * @param parameter
	 *            The parameter.
	 * @return The string.
	 */
	public String getString(String parameter) {
		return getParameter(parameter);
	}

	/**
	 * Gets the callback flag.
	 * 
	 * @return True if callback set, false if otherwise.
	 */
	public boolean isCallbackSet() {
		return getParameter("callback") != null;
	}

	/**
	 * Gets the error flag.
	 * 
	 * @return True if error, false if not.
	 */
	public boolean isError() {
		return packet.isError();
	}
}
