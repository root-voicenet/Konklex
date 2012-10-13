package org.apollo.util;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * A utility class which provides extra {@link ChannelBuffer}-related methods
 * which deal with data types used in the protocol.
 * @author Graham
 */
public final class ChannelBufferUtil {

	/**
	 * Reads a string from the specified buffer.
	 * @param buffer The buffer.
	 * @return The string.
	 */
	public static String readString(ChannelBuffer buffer) {
		final StringBuilder builder = new StringBuilder();
		int character;
		while (buffer.readable() && (character = buffer.readUnsignedByte()) != 10)
			builder.append((char) character);
		return builder.toString();
	}

	/**
	 * Default private constructor to prevent instantiation by other classes.
	 */
	private ChannelBufferUtil() {
	}
}
