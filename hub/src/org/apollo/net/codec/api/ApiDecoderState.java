package org.apollo.net.codec.api;

/**
 * An enumeration with the different states the {@link ApiDecoder} can be in.
 * @author Steve
 */
public enum ApiDecoderState {
	/**
	 * The login handshake state will write the uptime.
	 */
	LOGIN_HANDSHAKE,
	/**
	 * The login header state will wait for the payload length to be received. These are saved, and then the state will
	 * be set to the login payload state.
	 */
	LOGIN_HEADER,
	/**
	 * The login payload state will wait for all login information (such as password).
	 */
	LOGIN_PAYLOAD;
}
