package org.apollo.backend.method.handler.impl;

import org.apollo.backend.codec.session.FrontendChannel;
import org.apollo.backend.method.handler.MethodHandler;
import org.apollo.backend.method.handler.MethodHandlerContext;
import org.apollo.backend.method.impl.StatusMethod;

/**
 * An {@link MethodHandler} for the {@link StatusMethod}
 * @author Steve
 */
public final class StatusMethodHandler extends MethodHandler<StatusMethod> {

    @Override
    public void handle(MethodHandlerContext ctx, FrontendChannel channel, StatusMethod method) {
	channel.send(new StatusMethod());
    }
}
