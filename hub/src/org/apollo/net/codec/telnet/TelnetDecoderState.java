package org.apollo.net.codec.telnet;

/**
 * An enumeration with the different states the {@link TelnetDecoder} can be in.
 * @author Steve
 */
public enum TelnetDecoderState {
	/**
	 * The username authentication.
	 */
	USER,
	/**
	 * The password authentication.
	 */
	PASS;
}
