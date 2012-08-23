package org.apollo.backend.method.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apollo.backend.codec.session.FrontendChannel;
import org.apollo.backend.method.handler.MethodHandler;
import org.apollo.backend.method.handler.MethodHandlerContext;
import org.apollo.backend.method.impl.NotificationMethod;
import org.apollo.backend.method.impl.RegisterMethod;

/**
 * An {@link MethodHandler} for the {@link RegisterMethod}
 * @author Steve
 */
public final class RegisterMethodHandler extends MethodHandler<RegisterMethod> {

	/**
	 * The channels receiving push notifications.
	 */
	private static final List<FrontendChannel> channels = new ArrayList<FrontendChannel>();

	@Override
	public void handle(MethodHandlerContext ctx, final FrontendChannel channel, RegisterMethod method) {
		if (ctx.isStreaming()) {
			channels.add(channel);
			channel.send(new NotificationMethod("stream", "success"));
		} else {
			channel.close();
		}
	}

	/**
	 * Pushes a notification to the channels.
	 * @param key The key.
	 * @param value The value.
	 */
	public static void pushNotification(String key, String value) {
		List<FrontendChannel> temp = new ArrayList<FrontendChannel>(channels.size());
		for (FrontendChannel channel : channels) {
			if (channel.isRemoved()) {
				temp.remove(channel);
			} else {
				channel.send(new NotificationMethod(key, value));
			}
		}
		channels.removeAll(temp);
	}
}