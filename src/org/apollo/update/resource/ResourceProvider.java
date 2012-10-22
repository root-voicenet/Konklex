package org.apollo.update.resource;

import java.io.IOException;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;

/**
 * A class which provides resources.
 * @author Graham
 */
public abstract class ResourceProvider {

	/**
	 * Checks that this provider can fulfill a request to the specified resource.
	 * @param path The path to the resource, e.g. {@code /crc}.
	 * @return {@code true} if the provider can fulfill a request to the resource, {@code false} otherwise.
	 * @throws IOException if an I/O error occurs.
	 */
	public abstract boolean accept(String path) throws IOException;

	/**
	 * Gets a resource by its path.
	 * @param request The http request.
	 * @param channel The channel.
	 * @param path The path.
	 * @return The resource, or {@code null} if it doesn't exist.
	 * @throws IOException if an I/O error occurs.
	 */
	public abstract Object get(Channel channel, HttpRequest request, String path) throws IOException;
}
