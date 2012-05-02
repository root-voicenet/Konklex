package org.apollo.backend.codec;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	 * Creates a new frontend packet.
	 * @param parameters The parameters.
	 */
	public FrontendPacket(Map<String, List<String>> parameters) {
		this.parameters = parameters;
	}

	/**
	 * Creates a new frontend packet.
	 * @param uri The uri being written or reading.
	 * @throws IOException TextUtil::getUrlParameters();
	 */
	public FrontendPacket(String uri) throws IOException {
		parameters = TextUtil.getUrlParameters(uri);
		for (Entry<String, List<String>> kv : parameters.entrySet()) {
			for (String okv : kv.getValue()) {
				okv.replaceFirst("\\[", "").replaceFirst("\\]", "");
			}
		}
	}

	/**
	 * Gets the parameter.
	 * @param parameter The parameter.
	 * @return The paramter's value.
	 */
	public String getParameter(String parameter) {
		return parameters.get(parameter).toString();
	}
}
