package org.apollo.net.codec.api;

import org.apollo.api.Frontend;
import org.apollo.api.method.Method;
import org.apollo.api.method.MethodEncoder;
import org.apollo.net.codec.game.GamePacket;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * A {@link OneToOneEncoder} which encodes {@link Method}s into {@link GamePacket}s.
 * @author Steve
 */
public final class ApiMethodEncoder extends OneToOneEncoder {

	/**
	 * The frontend.
	 */
	private final Frontend frontend;

	/**
	 * Creates the game event encoder with the specified frontend.
	 * @param frontend The frontend.
	 */
	public ApiMethodEncoder(Frontend frontend) {
		this.frontend = frontend;
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
			final MethodEncoder<Method> encoder = (MethodEncoder<Method>) frontend
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
