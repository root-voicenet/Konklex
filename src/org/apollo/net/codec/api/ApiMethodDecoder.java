package org.apollo.net.codec.api;

import org.apollo.api.method.Method;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.release.MethodDecoder;
import org.apollo.net.release.Release;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

/**
 * A {@link OneToOneDecoder} that decodes {@link GamePacket}s into {@link Method}s.
 * @author Steve
 */
public final class ApiMethodDecoder extends OneToOneDecoder {

	/**
	 * The current release.
	 */
	private final Release release;

	/**
	 * Creates the game event decoder with the specified release.
	 * @param release The release.
	 */
	public ApiMethodDecoder(Release release) {
		this.release = release;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.netty.handler.codec.oneone.OneToOneDecoder#decode(org.jboss .netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.Channel, java.lang.Object)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel c, Object msg) throws Exception {
		if (msg instanceof GamePacket) {
			final GamePacket packet = (GamePacket) msg;
			final MethodDecoder<?> decoder = release.getMethodDecoder(packet.getOpcode());
			if (decoder != null) {
				return decoder.decode(packet);
			}
			return null;
		}
		return msg;
	}
}
