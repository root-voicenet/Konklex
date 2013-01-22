package org.apollo.net.codec.api;

import org.apollo.net.codec.game.GameDecoderState;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.meta.PacketMetaData;
import org.apollo.net.meta.PacketType;
import org.apollo.net.release.Release;
import org.apollo.util.StatefulFrameDecoder;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * A {@link StatefulFrameDecoder} which decodes api packets.
 * @author Steve
 */
public final class ApiPacketDecoder extends StatefulFrameDecoder<GameDecoderState> {

	/**
	 * The current release.
	 */
	private final Release release;

	/**
	 * The current opcode.
	 */
	private int opcode;

	/**
	 * The packet type.
	 */
	private PacketType type;

	/**
	 * The current length.
	 */
	private int length;

	/**
	 * Creates the {@link ApiPacketDecoder}.
	 * @param release The current release.
	 */
	public ApiPacketDecoder(Release release) {
		super(GameDecoderState.GAME_OPCODE);
		this.release = release;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.util.StatefulFrameDecoder#decode(org.jboss.netty.channel. ChannelHandlerContext,
	 * org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer, java.lang.Enum)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer, GameDecoderState state)
			throws Exception {
		switch (state) {
		case GAME_OPCODE:
			return decodeOpcode(ctx, channel, buffer);
		case GAME_LENGTH:
			return decodeLength(ctx, channel, buffer);
		case GAME_PAYLOAD:
			return decodePayload(ctx, channel, buffer);
		default:
			throw new Exception("Invalid game decoder state");
		}
	}

	/**
	 * Decodes in the length state.
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The buffer.
	 * @return The frame, or {@code null}.
	 * @throws Exception if an error occurs.
	 */
	private Object decodeLength(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readable()) {
			length = buffer.readUnsignedByte();
			if (length == 0) {
				return decodeZeroLengthPacket(ctx, channel, buffer);
			}
			else {
				setState(GameDecoderState.GAME_PAYLOAD);
			}
		}
		return null;
	}

	/**
	 * Decodes in the opcode state.
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The buffer.
	 * @return The frame, or {@code null}.
	 * @throws Exception if an error occurs.
	 */
	private Object decodeOpcode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readable()) {
			final int encryptedOpcode = buffer.readUnsignedByte();
			opcode = encryptedOpcode;
			final PacketMetaData metaData = release.getIncomingApiPacketMetaData(opcode);
			if (metaData == null) {
				throw new Exception("Illegal opcode: " + opcode);
			}
			type = metaData.getType();
			switch (type) {
			case FIXED:
				length = metaData.getLength();
				if (length == 0) {
					return decodeZeroLengthPacket(ctx, channel, buffer);
				}
				else {
					setState(GameDecoderState.GAME_PAYLOAD);
				}
				break;
			case VARIABLE_BYTE:
				setState(GameDecoderState.GAME_LENGTH);
				break;
			default:
				throw new Exception("Illegal packet type: " + type);
			}
		}
		return null;
	}

	/**
	 * Decodes in the payload state.
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The buffer.
	 * @return The frame, or {@code null}.
	 * @throws Exception if an error occurs.
	 */
	private Object decodePayload(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (buffer.readableBytes() >= length) {
			final ChannelBuffer payload = buffer.readBytes(length);
			setState(GameDecoderState.GAME_OPCODE);
			return new GamePacket(opcode, type, payload);
		}
		return null;
	}

	/**
	 * Decodes a zero length packet. This hackery is required as Netty will throw an exception if we return a frame but
	 * have read nothing!
	 * @param ctx The channel handler context.
	 * @param channel The channel.
	 * @param buffer The buffer.
	 * @return The frame, or {@code null}.
	 * @throws Exception if an error occurs.
	 */
	private Object decodeZeroLengthPacket(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer)
			throws Exception {
		final ChannelBuffer payload = ChannelBuffers.buffer(0);
		setState(GameDecoderState.GAME_OPCODE);
		return new GamePacket(opcode, type, payload);
	}
}
