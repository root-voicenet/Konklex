package org.apollo.backend.codec;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apollo.backend.Frontend;
import org.apollo.backend.codec.session.FrontendSession;
import org.apollo.backend.method.Method;
import org.apollo.backend.method.MethodDecoder;
import org.apollo.util.TextUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An encoder which encodes the {@link FrontendPacket}s.
 * @author Steve
 */
public final class FrontendPacketDecoder {

    /**
     * The channel.
     */
    private final FrontendSession session;

    /**
     * Creates the frontend packet decoder.
     * @param session The frontend session.
     */
    public FrontendPacketDecoder(FrontendSession session) {
	this.session = session;
    }

    /**
     * Decodes the frontend packet that was created.
     * @param packet The packet.
     * @return The method, if any.
     */
    @SuppressWarnings("unchecked")
    private <E extends Method> E decode(FrontendPacket packet) {
	final FrontendPacketReader reader = new FrontendPacketReader(packet);
	session.setMethod(reader.getMethod());
	final MethodDecoder<E> decoder = (MethodDecoder<E>) Frontend.getInstance().getMethodDecoder(reader.getMethod());
	if (decoder != null)
	    try {
		return decoder.decode(packet);
	    } catch (final Exception e) {
		e.printStackTrace();
	    }
	return null;
    }

    /**
     * Decodes the uri into a packet structure.
     * @param uri The uri to decode.
     * @return The packet structure.
     */
    public <E extends Method> E decode(String uri) {
	boolean error = false;
	final JSONObject object = new JSONObject();
	final Map<String, List<Object>> parameters = TextUtil.getUrlParameters(uri);
	for (final Entry<String, List<Object>> kv : parameters.entrySet())
	    try {
		object.put(kv.getKey(), kv.getValue().toString().replaceFirst("\\[", "").replaceFirst("\\]", ""));
	    } catch (final JSONException e) {
		error = true;
		e.printStackTrace();
	    }
	return decode(new FrontendPacket(object, error));
    }
}
