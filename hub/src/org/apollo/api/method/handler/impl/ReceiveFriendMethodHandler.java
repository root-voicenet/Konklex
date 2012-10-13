package org.apollo.api.method.handler.impl;

import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.ReceiveFriendMethod;
import org.apollo.net.session.ProxyApiSession;

/**
 * An {@link MethodHandler} for the {@link ReceiveFriendMethod}
 * @author Steve
 */
public final class ReceiveFriendMethodHandler extends MethodHandler<ReceiveFriendMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, ReceiveFriendMethod method) {
		// final boolean online = Server.getContext().getServerChannelGroup().isPlayerOnline(NameUtil.decodeBase37(method.getFriend()));
		// session.send(new SendFriendMethod(method.getSender(), method.getFriend(), online ? 1 : 0));
	}

}
