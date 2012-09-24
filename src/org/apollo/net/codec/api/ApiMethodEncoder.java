package org.apollo.net.codec.api;

import org.apollo.api.method.Method;
import org.apollo.net.codec.game.GamePacket;
import org.apollo.net.release.MethodEncoder;
import org.apollo.net.release.Release;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * A {@link OneToOneEncoder} which encodes {@link Method}s into {@link GamePacket}s.
 * @author Steve
 */
public final class ApiMethodEncoder extends OneToOneEncoder {

	/**
	 * The current release.
	 */
	private final Release release;

	/**
	 * Creates the game event encoder with the specified release.
	 * 
	 * @param release
	 *            The release.
	 */
	public ApiMethodEncoder(Release release) {
		this.release = release;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss
	 * .netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel,
	 * java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel c, Object msg)
			throws Exception {
		if (msg instanceof Method) {
			final Method method = (Method) msg;
			final MethodEncoder<Method> encoder = (MethodEncoder<Method>) release
					.getMethodEncoder(method.getClass());
			if (encoder != null) {
				GamePacket packet = encoder.encode(method);
				return packet;
			}
			return null;
		}
		return msg;
	}
}
