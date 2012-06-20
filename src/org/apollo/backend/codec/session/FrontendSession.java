package org.apollo.backend.codec.session;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.backend.FrontendService;
import org.apollo.backend.codec.FrontendPacketDecoder;
import org.apollo.backend.codec.FrontendPacketEncoder;
import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.chain.MethodHandlerChain;
import org.apollo.backend.method.handler.chain.MethodHandlerChainGroup;
import org.apollo.backend.method.impl.ResponseMethod;
import org.apollo.game.GameConstants;
import org.apollo.game.model.World;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;

/**
 * A frontend session.
 * @author Steve
 */
public final class FrontendSession {

    /**
     * The logger for this class.
     */
    private static final Logger logger = Logger.getLogger(FrontendSession.class.getName());

    /**
     * The queue of pending {@link Method}s.
     */
    private final BlockingQueue<Method> methodQueue = new ArrayBlockingQueue<Method>(GameConstants.EVENTS_PER_PULSE);

    /**
     * The channel.
     */
    private final Channel channel;

    /**
     * The encoder.
     */
    private final FrontendPacketEncoder encoder;

    /**
     * The decoder.
     */
    private final FrontendPacketDecoder decoder;

    /**
     * The streaming flag.
     */
    private final boolean stream;

    /**
     * The method that was requested.
     */
    private String method;

    /**
     * Creates a login session for the specified channel.
     * @param channel The channel.
     * @param stream True if streaming, false if not.
     */
    public FrontendSession(Channel channel, boolean stream) {
	this.channel = channel;
	this.stream = stream;
	encoder = new FrontendPacketEncoder(channel);
	decoder = new FrontendPacketDecoder(this);
    }

    /**
     * Destroys this session.
     */
    public void close() {
	channel.write(ChannelBuffers.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	World.getWorld().getContext().getService(FrontendService.class).removeSession(this);
    }

    /**
     * Decodes a url.
     * @param path The url.
     */
    public <E extends Method> void decode(Map<String, List<String>> path) {
	final E packet = decoder.decode(path);
	if (packet != null) {
	    methodQueue.add(packet);
	} else {
	    send(new ResponseMethod(method, "This method is not defined.", true));
	}
    }

    /**
     * Decodes a url.
     * @param path The url.
     */
    public <E extends Method> void decode(String path) {
	final E packet = decoder.decode(path);
	if (packet != null) {
	    methodQueue.add(packet);
	} else {
	    send(new ResponseMethod(method, "This method is not defined.", true));
	}
    }

    /**
     * Gets the last requested method.
     * @return The last requested method.
     */
    public String getRequested() {
	return method;
    }

    /**
     * Handles pending methods for this session.
     * @param chainGroup The method chain group.
     */
    @SuppressWarnings("unchecked")
    public void handlePendingEvents(MethodHandlerChainGroup chainGroup) {
	if (!channel.isOpen() && !channel.isBound() && !channel.isConnected()) {
	    World.getWorld().getContext().getService(FrontendService.class).removeSession(this);
	    return;
	}
	Method method;
	while ((method = methodQueue.poll()) != null) {
	    Class<? extends Method> methodType = method.getClass();
	    MethodHandlerChain<Method> chain = (MethodHandlerChain<Method>) chainGroup.getChain(methodType);
	    while (chain == null && methodType != null) {
		methodType = (Class<? extends Method>) methodType.getSuperclass();
		if (methodType == Method.class) {
		    methodType = null;
		} else {
		    chain = (MethodHandlerChain<Method>) chainGroup.getChain(methodType);
		}
	    }
	    if (chain == null) {
		logger.warning("No chain for method: " + method.getClass().getName() + ".");
	    } else {
		try {
		    chain.handle(this, method);
		} catch (final Exception ex) {
		    logger.log(Level.SEVERE, "Error handling method.", ex);
		}
	    }
	}
    }

    /**
     * Gets the stream flag.
     * @return True if streaming, false if otherwise.
     */
    public boolean isStreaming() {
	return stream;
    }

    /**
     * Encodes and dispatches the specified method.
     * @param method The method.
     */
    public <E extends Method> void send(E method) {
	final Channel channel = this.channel;
	if (channel.isBound() && channel.isConnected() && channel.isOpen()) {
	    encoder.setAttribute(3, true);
	    encoder.setAttribute(2, stream);
	    encoder.encode(method);
	} else {
	    World.getWorld().getContext().getService(FrontendService.class).removeSession(this);
	}
    }

    /**
     * Sets the ipn flag.
     * @param ipn The ipn flag.
     */
    public void setIpn(boolean ipn) {
    }

    /**
     * Sets the frontend method.
     * @param method The method.
     */
    public void setMethod(String method) {
	this.method = method;
    }
}
