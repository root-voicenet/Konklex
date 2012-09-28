package org.apollo.api.method.handler.impl;

import org.apollo.api.ProxyApiSession;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.UpdateMethod;
import org.apollo.game.scheduling.impl.SystemUpdateTask;

/**
 * An {@link MethodHandler} for the {@link UpdateMethod}
 * @author Steve
 */
public final class UpdateMethodHandler extends MethodHandler<UpdateMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, UpdateMethod method) {
		SystemUpdateTask.start(method.getTime());
	}
	
}