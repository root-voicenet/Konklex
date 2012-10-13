package org.apollo.net.codec.api;

import org.apollo.api.Frontend;
import org.apollo.api.method.MethodDecoder;
import org.apollo.net.codec.game.GamePacket;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

/**
 * A {@link OneToOneDecoder} that decodes {@link GamePacket}s into {@link Method}s.
 * @author Steve
 */
public final class ApiMethodDecoder extends OneToOneDecoder {

	/**
	 * The frontend.
	 */
	private final Frontend frontend;

	/**
	 * Creates the game event decoder with the specified release.
	 * @param frontend The frontend.
	 */
	public ApiMethodDecoder(Frontend frontend) {
		this.frontend = frontend;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.jboss.netty.handler.codec.oneone.OneToOneDecoder#decode(org.jboss
	 * .netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * java.lang.Object)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel c, Object msg) throws Exception {
		if (msg instanceof GamePacket) {
			final GamePacket packet = (GamePacket) msg;
			final MethodDecoder<?> decoder = frontend.getMethodDecoder(packet.getOpcode());
			if (decoder != null) {
				return decoder.decode(packet);
			}
			return null;
		}
		return msg;
	}
}
