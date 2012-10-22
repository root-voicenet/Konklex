package org.apollo.net.codec.api;

import org.apollo.ServerContext;
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
	 * The time.
	 */
	private int time;

	/**
	 * The context.
	 */
	private final ServerContext context;

	/**
	 * Creates the api decoder with the default initial state.
	 */
	public ApiDecoder(ServerContext context) {
		super(ApiDecoderState.LOGIN_HANDSHAKE);
		this.context = context;
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
			buffer.readByte();
			if (buffer.readable()) {
				return new Object[] { time, buffer.readBytes(buffer.readableBytes()) };
			} else {
				return time;
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
		if (buffer.readable()) {
			buffer.readByte();
			ChannelBuffer resp = ChannelBuffers.dynamicBuffer();
			int nodes = context.getServerChannelGroup().size();
			resp.writeByte(6 * nodes);
			resp.writeByte(nodes);
			for (int i = 0; i < nodes; i++) {
				final World world = context.getServerChannelGroup().getWorld(i + 1);
				resp.writeShort(world.getPlayers().size());
				resp.writeShort(world.getNpcs());
				resp.writeShort(world.getTime());
			}
			channel.write(resp);
			setState(ApiDecoderState.LOGIN_PAYLOAD);
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
		if (buffer.readableBytes() >= 9) {
			ChannelBuffer resp = ChannelBuffers.buffer(8);
			buffer.readByte();
			time = (int) buffer.readLong();
			resp.writeLong(1234);
			channel.write(resp);
			setState(ApiDecoderState.LOGIN_HEADER);
		}
		return null;
	}
}
