package org.apollo.update.resource;

import java.io.IOException;
import java.util.ArrayList;

import org.apollo.backend.FrontendService;
import org.apollo.backend.codec.session.FrontendSession;
import org.apollo.backend.method.impl.ResponseMethod;
import org.apollo.game.model.World;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;

/**
 * A {@link ResourceProvider} which provides frontend resources.
 * @author Steve
 */
public final class FrontendResourceProvider extends ResourceProvider {

	/**
	 * Gets the ipn function.
	 * @param path The path of the url.
	 * @return The ipn function call.
	 */
	private String getCall(String path) {
		if (path.contains("onebip")) {
			return "onebipIpn";
		} else if (path.contains("paypal")) {
			return "paypalIpn";
		} else {
			return "ipn";
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.apollo.update.resource.ResourceProvider#accept(java.lang.String)
	 */
	@Override
	public boolean accept(String path) throws IOException {
		return path.contains("/api") || path.contains("/ipn");
	}

	/**
	 * Gets a resource by its path.
	 * @param channel The channel.
	 * @param request The http request.
	 * @param path The path.
	 * @return True if accepted, false if otherwise.
	 * @throws IOException If a I/O exception occurs.
	 */
	@Override
	public Object get(Channel channel, HttpRequest request, String path) throws IOException {
		ChannelBuffer content = request.getContent();
		final FrontendService service = World.getWorld().getContext().getService(FrontendService.class);
		if (service != null) {
			final FrontendSession session = new FrontendSession(channel, path.startsWith("/api/stream"));
			try {
				QueryStringDecoder decoder = null;
				if (request.getMethod().equals(HttpMethod.GET)) {
					decoder = new QueryStringDecoder(path);
				} else if (request.getMethod().equals(HttpMethod.POST)) {
					decoder = new QueryStringDecoder("?" + content.toString(CharsetUtil.UTF_8));
				}
				if (path.startsWith("/ipn")) {
					ArrayList<String> inject = new ArrayList<String>();
					inject.add(getCall(path));
					decoder.getParameters().put("method", inject);
				}
				session.decode(decoder.getParameters());
			} catch (Exception e) {
				session.send(new ResponseMethod(null, e.toString(), true));
			}
			service.addSession(session);
			return 2;
		} else {
			return 1;
		}
	}
}
