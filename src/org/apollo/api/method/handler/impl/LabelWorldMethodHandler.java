package org.apollo.api.method.handler.impl;

import org.apollo.api.ProxyApiSession;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.LabelWorldMethod;
import org.apollo.game.model.World;

/**
 * An {@link MethodHandler} for the {@link LabelWorldMethod}
 * @author Steve
 */
public final class LabelWorldMethodHandler extends MethodHandler<LabelWorldMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, LabelWorldMethod method) {
		World.getWorld().setId(method.getWorld());
	}

}
