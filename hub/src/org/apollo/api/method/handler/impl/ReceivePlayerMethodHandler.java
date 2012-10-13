package org.apollo.api.method.handler.impl;

import org.apollo.Server;
import org.apollo.ServerContext;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.ReceivePlayerMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.net.session.ProxyApiSession;
import org.apollo.util.NameUtil;

/**
 * An {@link MethodHandler} for the {@link ReceivePlayerMethod}
 * @author Steve
 */
public final class ReceivePlayerMethodHandler extends MethodHandler<ReceivePlayerMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, ReceivePlayerMethod method) {
		final ServerContext context = Server.getContext();
		if (context.getServerChannelGroup().contains(method.getWorld())) {
			final World world = context.getServerChannelGroup().getWorld(method.getWorld());
			final String name = NameUtil.decodeBase37(method.getPlayer());
			switch(method.getStatus()) {
				case 1:
					world.getPlayers().put(name, new Player(method.getRights()));
					break;
				case 0:
					world.getPlayers().remove(name);
					break;
			}
			Server.getContext().getServerChannelGroup().write(method, method.getWorld(), false);
		}
	}

}
