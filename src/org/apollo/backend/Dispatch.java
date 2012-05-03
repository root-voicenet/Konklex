package org.apollo.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apollo.backend.codec.FrontendPacketBuilder;
import org.apollo.backend.method.Method;
import org.apollo.backend.util.RequestMethod;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * HTTP API calls.
 * @author Steve
 */
public final class Dispatch {

	/**
	 * The instance.
	 */
	private static final Dispatch instance = new Dispatch();

	/**
	 * Return the instance.
	 * @return {@link Dispatch}
	 */
	public static Dispatch getInstance() {
		return instance;
	}

	/**
	 * Prevent instantation.
	 */
	private Dispatch() {
	}

	/**
	 * Clean the arguments from the api call.
	 * @param args the args
	 * @return {@link String}
	 */
	private String[] clean(String[] args) {
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].length() > 0 && !args[i].equals("[]")) {
					args[i] = args[i];
				}
			}
		} catch (Exception e) {
		}
		return args;
	}

	/**
	 * Decode a api call.
	 * @param url The url parameters.
	 * @return {@link ChannelBuffer}
	 * @throws JSONException the jSON exception
	 * @throws UnsupportedEncodingException
	 */
	public ChannelBuffer decode(Map<String, List<String>> url) throws JSONException, UnsupportedEncodingException {
		final String method = getMethod(url), key = getKey(url), response;
		final String[] args = clean(getArgs(url));
		final boolean iserror;
		JSONObject result = new JSONObject();
		result.put("method", method);
		if (key.equals("UUfp6WcA")) {
			Method getMethod = Manager.getInstance().getMethod(method);
			if (getMethod != null) {
				getMethod.setRequested(new RequestMethod(method, args));
				if (getMethod.getSize() == args.length) {
					response = Manager.getInstance().getHandler(getMethod).handle(getMethod).toString();
					iserror = Manager.getInstance().getHandler(getMethod).isError();
				} else {
					response = "Invalid argument size.";
					iserror = true;
				}
			} else {
				response = "Method not implemented.";
				iserror = true;
			}
		} else {
			response = "Invalid key.";
			iserror = true;
		}
		result.put("response", response);
		result.put("error", iserror ? true : false);
		String callback = getCallback(url);
		FrontendPacketBuilder builder = new FrontendPacketBuilder("method");
		builder.addParameter("test", "lmfao hi");
		builder.toFrontendPacket();
		return ChannelBuffers.copiedBuffer(callback != null ? callback + "(" + result.toString() + ");" : result.toString(), Charset.defaultCharset());
	}

	/**
	 * Gets the arguments.
	 * @param args The arguments.
	 * @return {@link String} The arguments in {@code String[]} format.
	 * @throws UnsupportedEncodingException
	 */
	public String[] getArgs(Map<String, List<String>> args) throws UnsupportedEncodingException {
		String params = URLDecoder.decode(args.get("args").toString().replaceFirst("\\[", "").replaceFirst("\\]", ""), "UTF-8");
		return new Gson().fromJson(params, String[].class);
	}

	/**
	 * Gets the callback.
	 * @param method The method.
	 * @return {@link String} The callback.
	 */
	public String getCallback(Map<String, List<String>> method) {
		return method.get("callback").toString().replaceFirst("\\[", "").replaceFirst("\\]", "");
	}

	/**
	 * Gets the key.
	 * @param key The key.
	 * @return {@link String} The key in {@code String} format.
	 */
	public String getKey(Map<String, List<String>> key) {
		return key.get("key").toString().replaceFirst("\\[", "").replaceFirst("\\]", "");
	}

	/**
	 * Gets the method.
	 * @param method The method.
	 * @return {@link String} The method in {@code String} format.
	 */
	public String getMethod(Map<String, List<String>> method) {
		return method.get("method").toString().replaceFirst("\\[", "").replaceFirst("\\]", "");
	}
}
