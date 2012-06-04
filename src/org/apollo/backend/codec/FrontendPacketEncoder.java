package org.apollo.backend.codec;

import java.io.UnsupportedEncodingException;
import java.util.BitSet;
import java.util.Date;

import org.apollo.backend.Frontend;
import org.apollo.backend.method.Method;
import org.apollo.backend.method.MethodEncoder;
import org.apollo.update.HttpRequestWorker;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.json.JSONObject;

/**
 * An encoder which encodes the {@link FrontendPacket}s.
 * @author Steve
 */
public final class FrontendPacketEncoder {

    /**
     * The channel to encode too.
     */
    private final Channel channel;

    /**
     * The attributes of this class.
     */
    private final BitSet attributes = new BitSet();

    /**
     * Creates the frontend packet encoder.
     * @param channel The channel to encode too.
     */
    public FrontendPacketEncoder(Channel channel) {
	this.channel = channel;
    }

    /**
     * Appends the error flag to the json.
     * @return The frontend packet encoder.
     */
    private String addError(FrontendPacket packet) {
	final JSONObject object = packet.getParameters();
	try {
	    object.put("error", packet.isError());
	} catch (final Exception e) {
	    e.printStackTrace();
	}
	return object.toString();
    }

    /**
     * Encodes the method.
     * @param method The method to encode.
     */
    @SuppressWarnings("unchecked")
    public <E extends Method> void encode(E method) {
	final MethodEncoder<E> encoder = (MethodEncoder<E>) Frontend.getInstance().getMethodEncoder(method.getClass());
	if (encoder != null) {
	    final FrontendPacket packet = encoder.encode(method);
	    encode(packet);
	    if (getAttribute(2)) {
		if (channel.getPipeline().get("timeout") != null)
		    channel.getPipeline().remove("timeout");
	    } else
		attributes.clear();
	}
    }

    /**
     * Encodes the packet.
     * @param packet The packet.
     */
    private void encode(FrontendPacket packet) {
	final String encodedString = !getAttribute(3) ? packet.getParameters().toString() : addError(packet);
	final boolean isStreaming = getAttribute(2);
	if (!getAttribute(1)) {
	    final HttpResponse resp = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
	    resp.setHeader("Date", new Date());
	    resp.setHeader("Server", HttpRequestWorker.SERVER_IDENTIFIER);
	    resp.setHeader("Content-type", "application/json, charset=" + HttpRequestWorker.CHARACTER_SET.name());
	    resp.setHeader("Cache-control", "no-cache");
	    resp.setHeader("Pragma", "no-cache, must-revalidate");
	    resp.setHeader("Expires", new Date(0));
	    resp.setHeader("Connection", isStreaming ? "keep-alive" : "close");
	    if (!isStreaming) {
		final ChannelBuffer wrappedBuf = ChannelBuffers.wrappedBuffer(encodedString.getBytes());
		resp.setHeader("Content-length", wrappedBuf.readableBytes());
		resp.setChunked(false);
		resp.setContent(wrappedBuf);
	    }
	    channel.write(resp);
	    setAttribute(1, true);
	}
	if (isStreaming)
	    writeStream(encodedString);
	else
	    channel.getCloseFuture().addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * Gets the attribute.
     * @param key The key.
     * @return The value.
     */
    public boolean getAttribute(int key) {
	return attributes.get(key);
    }

    /**
     * Sets the attribute.
     * @param key The key.
     * @param value The value.
     */
    public void setAttribute(int key, boolean value) {
	attributes.set(key, value);
    }

    /**
     * Writes a string to the stream.
     * @param encodedString The string to write.
     */
    private void writeStream(String encodedString) {
	try {
	    final byte[] toWrite = (encodedString.trim() + "\r\n").getBytes("UTF-8");
	    final ChannelBuffer buf = ChannelBuffers.buffer(toWrite.length);
	    buf.writeBytes(toWrite);
	    channel.write(buf);
	    channel.write(ChannelBuffers.EMPTY_BUFFER);
	} catch (final UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
    }
}
