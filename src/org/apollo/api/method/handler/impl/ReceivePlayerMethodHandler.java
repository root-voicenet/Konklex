package org.apollo.api.method.handler.impl;

import org.apollo.api.ProxyApiSession;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.SendPlayerMethod;
import org.apollo.game.model.World;
import org.apollo.util.NameUtil;

/**
 * An {@link MethodHandler} for the {@link SendPlayerMethod}
 * @author Steve
 */
public final class ReceivePlayerMethodHandler extends MethodHandler<SendPlayerMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, SendPlayerMethod method) {
		if (method.isOnline())
			World.getWorld().getMessaging().register(NameUtil.decodeBase37(method.getPlayer()), method.getWorld());
		else
			World.getWorld().getMessaging().deregister(NameUtil.decodeBase37(method.getPlayer()), method.getWorld());
	}

}
