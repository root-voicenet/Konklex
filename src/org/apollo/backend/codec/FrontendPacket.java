package org.apollo.backend.codec;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apollo.util.TextUtil;

/**
 * The default packet for sending the codec information to the frontend.
 * @author Steve
 */
public final class FrontendPacket {

	/**
	 * The parameters requested or being sent.
	 */
	private final Map<String, List<String>> parameters;

	/**
	 * The error flag.
	 */
	private final boolean error;

	/**
	 * Creates a new frontend packet.
	 * @param parameters The parameters.
	 * @param error The error flag.
	 */
	public FrontendPacket(Map<String, List<String>> parameters, boolean error) {
		this.parameters = parameters;
		this.error = error;
	}

	/**
	 * Creates a new frontend packet.
	 * @param uri The uri being written or reading.
	 * @param error The error flag.
	 * @throws IOException TextUtil::getUrlParameters();
	 */
	public FrontendPacket(String uri, boolean error) throws IOException {
		parameters = TextUtil.getUrlParameters(uri);
		this.error = error;
	}

	/**
	 * Gets the method.
	 * @return The method.
	 */
	public String getMethod() {
		return getParameter("method");
	}

	/**
	 * Gets the parameter.
	 * @param parameter The parameter.
	 * @return The paramter's value.
	 */
	public String getParameter(String parameter) {
		return parameters.get(parameter).toString();
	}

	/**
	 * Gets the whole list of parameters.
	 * @return The list of parameters.
	 */
	public Map<String, List<String>> getParameters() {
		return parameters;
	}

	/**
	 * Gets the error flag.
	 * @return True if error, false if not.
	 */
	public boolean isError() {
		return error;
	}
}
