package org.apollo.backend.method.handler.chain;

import org.apollo.backend.codec.session.FrontendChannel;
import org.apollo.backend.codec.session.FrontendSession;
import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.MethodHandler;
import org.apollo.backend.method.handler.MethodHandlerContext;

/**
 * A chain of method handlers.
 * @param <E> The type of method the handlers in this chain handle.
 * @author Steve
 */
public final class MethodHandlerChain<E extends Method> {

    /**
     * The handlers.
     */
    private MethodHandler<E>[] handlers;

    /**
     * Creates the method handler chain.
     * @param handlers The handlers.
     */
    @SafeVarargs
    public MethodHandlerChain(MethodHandler<E>... handlers) {
	this.handlers = handlers;
    }

    /**
     * Dynamically adds an method handler to the end of the chain.
     * @param handler The handler.
     */
    @SuppressWarnings("unchecked")
    public void addLast(MethodHandler<E> handler) {
	final MethodHandler<E>[] old = handlers;
	handlers = new MethodHandler[old.length + 1];
	System.arraycopy(old, 0, handlers, 0, old.length);
	handlers[old.length] = handler;
    }

    /**
     * Handles the method, passing it down the chain until the chain is broken
     * or the event reaches the end of the chain.
     * @param session The frontend session.
     * @param method The method.
     */
    public void handle(final FrontendSession session, E method) {
	final boolean[] running = new boolean[1];
	running[0] = true;
	final MethodHandlerContext ctx = new MethodHandlerContext() {

	    @Override
	    public void breakHandlerChain() {
		running[0] = false;
	    }

	    @Override
	    public String getRequested() {
		return session.getRequested();
	    }
	};
	for (final MethodHandler<E> handler : handlers) {
	    handler.handle(ctx, new FrontendChannel(session), method);
	    if (!running[0])
		break;
	}
    }
}
