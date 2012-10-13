package org.apollo.api.method.handler.impl;

import org.apollo.Server;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.net.session.ProxyApiSession;
import org.apollo.util.NameUtil;

/**
 * An {@link MethodHandler} for the {@link PrivateChatMethod}
 * @author Steve
 */
public final class PrivateChatMethodHandler extends MethodHandler<PrivateChatMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, PrivateChatMethod method) {
		final String friend = NameUtil.decodeBase37(method.getFriend());
		if (Server.getContext().getServerChannelGroup().isPlayerOnline(friend)) {
			final int world = Server.getContext().getServerChannelGroup().get(friend);
			if (Server.getContext().getServerChannelGroup().contains(world))
				Server.getContext().getServerChannelGroup().get(world).write(method);
		}
	}

}
