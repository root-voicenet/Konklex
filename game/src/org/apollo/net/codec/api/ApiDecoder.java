package org.apollo.net.codec.api;

import org.apollo.game.model.World;
import org.apollo.util.StatefulFrameDecoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * A {@link FrameDecoder} for the 'api' protocol.
 * @author Steve
 */
public final class ApiDecoder extends StatefulFrameDecoder<ApiDecoderState> {

	/**
	 * The api packet length.
	 */
	private int apiLength;

	/**
	 * The nodes.
	 */
	private int nodes;

	/**
	 * Creates the api decoder with the default initial state.
	 */
	public ApiDecoder() {
		super(ApiDecoderState.LOGIN_HANDSHAKE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.util.StatefulFrameDecoder#decode(org.jboss.netty.channel. ChannelHandlerContext,
	 * org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer, java.lang.Enum)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, ApiDecoderState state)
			throws Exception {
		switch (state) {
		case LOGIN_HANDSHAKE:
			return decodeHandshake(ctx, channel, buffer);
		case LOGIN_HEADER:
			return decodeHeader(ctx, channel, buffer);
		case LOGIN_PAYLOAD:
			return decodePayload(ctx, channel, buffer);
		default:
			throw new Exception("Invalid login decoder state");
		}
	}

	/**
	 * Decodes in the payload state.
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The buffer.
	 * @return The frame, or {@code null}.
	 * @throws Exception if an error occurs.
	 */
	private Object decodePayload(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
		if (buffer.readable()) {
			apiLength = buffer.readByte();
			nodes = buffer.readByte();
			if (buffer.readableBytes() >= apiLength) {
				final ChannelBuffer payload = buffer.readBytes(apiLength);
				final int[][] worlds = new int[nodes][3];
				for (int i = 0; i < nodes; i++) {
					worlds[i][0] = payload.readUnsignedShort(); // players
					worlds[i][1] = payload.readUnsignedShort(); // npcs
					worlds[i][2] = payload.readUnsignedShort(); // time
				}
				ChannelBuffer buf = ChannelBuffers.buffer(1);
				buf.writeByte(1);
				channel.write(buf);
				final ApiRequest req = new ApiRequest(nodes, worlds);
				if (buffer.readable())
					return new Object[] { req, buffer.readBytes(buffer.readableBytes()) };
				else {
					return req;
				}
			}
		}
		return null;
	}

	/**
	 * Decodes in the header state.
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The buffer.
	 * @return The frame, or {@code null}.
	 * @throws Exception if an error occurs.
	 */
	private Object decodeHeader(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.writable() && buffer.readableBytes() >= 8) {
			buffer.readByte();
			if (buffer.readLong() == 1234) {
				ChannelBuffer buf = ChannelBuffers.buffer(1);
				buf.writeByte(1);
				channel.write(buf);
				setState(ApiDecoderState.LOGIN_PAYLOAD);
			}
			else {
				throw new Exception("Invalid password");
			}
		}
		return null;
	}

	/**
	 * Decodes in the handshake state.
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The buffer.
	 * @return The frame, or {@code null}.
	 * @throws Exception if an error occurs.
	 */
	private Object decodeHandshake(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
		final ChannelBuffer resp = ChannelBuffers.buffer(9);
		resp.writeByte(1); // 1
		resp.writeLong(World.getWorld().getUptime()); // 8
		channel.write(resp);
		setState(ApiDecoderState.LOGIN_HEADER);
		return null;
	}
}
