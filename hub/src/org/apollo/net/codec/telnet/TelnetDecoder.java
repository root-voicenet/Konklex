package org.apollo.net.codec.telnet;

import org.apollo.util.StatefulFrameDecoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * A {@link FrameDecoder} for the 'telnet' protocol.
 * @author Steve
 */
public final class TelnetDecoder extends StatefulFrameDecoder<TelnetDecoderState> {

	/**
	 * Creates the telnet decoder with the default initial state.
	 */
	public TelnetDecoder() {
		super(TelnetDecoderState.USER);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apollo.util.StatefulFrameDecoder#decode(org.jboss.netty.channel.
	 * ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * org.jboss.netty.buffer.ChannelBuffer, java.lang.Enum)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, TelnetDecoderState state) throws Exception {
		switch (state) {
		case USER:
			return decodeUsername(ctx, channel, buffer);
		case PASS:
			return decodePassword(ctx, channel, buffer);
		default:
			throw new Exception("Invalid login decoder state");
		}
	}

	/**
	 * Decodes the password.
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The readable buffer which should contain the password.
	 * @return True if authenticated successfully, false if otherwise.
	 */
	private Object decodePassword(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
		if (buffer.readable()) {
			// TODO read the buffer for a string
			// return true if successful
		}
		return null;
	}

	/**
	 * Decodes the username.
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The readable buffer which should contain the username.
	 * @return Always null.
	 */
	private Object decodeUsername(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
		if (buffer.readable()) {
			// TODO read the buffer for a string
			// always return null because we are not ready
		}
		return null;
	}
}
