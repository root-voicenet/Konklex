package org.apollo.api.method.handler.impl;

import org.apollo.Server;
import org.apollo.ServerContext;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.ReceiveFriendMethod;
import org.apollo.api.method.impl.TimeMethod;
import org.apollo.game.model.World;
import org.apollo.net.session.ProxyApiSession;

/**
 * An {@link MethodHandler} for the {@link ReceiveFriendMethod}
 * @author Steve
 */
public final class TimeMethodHandler extends MethodHandler<TimeMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, TimeMethod method) {
		final ServerContext context = Server.getContext();
		if (context.getServerChannelGroup().contains(method.getWorld())) {
			final World world = context.getServerChannelGroup().getWorld(method.getWorld());
			world.setCpu(method.getCpu());
			world.setItems(method.getItems());
			world.setNpcs(method.getNpcs());
			world.setObjects(method.getObjects());
			world.setRam(method.getRam());
			world.setRegions(world.getRegions());
			world.setStatus(method.getStatus());
			world.setThreads(world.getThreads());
			world.setTime((int) method.getTime());
		}
	}

}
