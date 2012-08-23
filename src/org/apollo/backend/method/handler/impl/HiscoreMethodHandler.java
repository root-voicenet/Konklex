package org.apollo.backend.method.handler.impl;

import org.apollo.backend.codec.session.FrontendChannel;
import org.apollo.backend.method.handler.MethodHandler;
import org.apollo.backend.method.handler.MethodHandlerContext;
import org.apollo.backend.method.impl.HiscoreMethod;

/**
 * An {@link MethodHandler} for the {@link HiscoreMethod}
 * 
 * @author Steve
 */
public final class HiscoreMethodHandler extends MethodHandler<HiscoreMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, final FrontendChannel channel,
			HiscoreMethod method) {
		channel.send(new HiscoreMethod(method.getUser()));
	}
}
