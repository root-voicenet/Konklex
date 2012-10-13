package org.apollo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.SocketAddress;
import org.apollo.api.Frontend;
import org.apollo.api.method.handler.chain.MethodHandlerChainGroup;
import org.apollo.io.MethodHandlerChainParser;
import org.apollo.util.ApolloServerChannelGroup;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;

/**
 * A {@link ServerContext} is created along with the {@link Server} object. The primary difference is that a reference
 * to the current context should be passed around within the server. The {@link Server} should not be as it allows
 * access to some methods such as
 * {@link Server#bind(java.net.SocketAddress, java.net.SocketAddress, java.net.SocketAddress)} which user scripts/code
 * should not be able to access.
 * @author Graham
 */
public final class ServerContext {

	/**
	 * The service manager.
	 */
	private final ServiceManager serviceManager;

	/**
	 * The channel group.
	 */
	private final ChannelGroup group = new DefaultChannelGroup();

	/**
	 * The server channel group.
	 */
	private final ApolloServerChannelGroup serverGroup = new ApolloServerChannelGroup();

	/**
	 * The client bootstrap.
	 */
	private final ClientBootstrap clientBootstrap;

	/**
	 * The method handler chain group.
	 */
	private final MethodHandlerChainGroup methodHandlerChainGroup;

	/**
	 * The frontend.
	 */
	private final Frontend frontend;

	/**
	 * Creates a new server context.
	 * @param frontend The frontend.
	 * @param serviceManager The service manager.
	 * @param clientBootstrap The client bootstrap.
	 * @throws Exception Methods cannot be loaded.
	 */
	ServerContext(Frontend frontend, ServiceManager serviceManager, ClientBootstrap clientBootstrap) throws Exception {
		this.frontend = frontend;
		this.serviceManager = serviceManager;
		this.serviceManager.setContext(this);
		this.clientBootstrap = clientBootstrap;
		final InputStream is = new FileInputStream("data/methods.xml");
		try {
			final MethodHandlerChainParser chainGroupParser = new MethodHandlerChainParser(is);
			this.methodHandlerChainGroup = chainGroupParser.parse();
		}
		finally {
			is.close();
		}
	}

	/**
	 * Gets a service. This method is shorthand for {@code getServiceManager().getService(...)}.
	 * @param <S> The type of service.
	 * @param clazz The service class.
	 * @return The service, or {@code null} if it could not be found.
	 */
	public <S extends Service> S getService(Class<S> clazz) {
		return serviceManager.getService(clazz);
	}

	/**
	 * Gets the service manager.
	 * @return The service manager.
	 */
	public ServiceManager getServiceManager() {
		return serviceManager;
	}

	/**
	 * Gets the channel group.
	 * @return The channel group.
	 */
	public ChannelGroup getChannelGroup() {
		return group;
	}

	/**
	 * Gets the server channel group.
	 * @return The server channel group.
	 */
	public ApolloServerChannelGroup getServerChannelGroup() {
		return serverGroup;
	}

	/**
	 * Connects this hub to a server.
	 * @param address The address of the server to connect to.
	 * @param persistant The persistence.
	 * @return The future channel.
	 */
	public ChannelFuture connect(SocketAddress address, boolean persistant) {
		// TODO Implement persistence
		return clientBootstrap.connect(address);
	}

	/**
	 * Gets the method handler chain group.
	 * @return The method handler chain group.
	 */
	public MethodHandlerChainGroup getMethodHandlerChainGroup() {
		return methodHandlerChainGroup;
	}

	/**
	 * Gets the frontend.
	 * @return The frontend.
	 */
	public Frontend getFrontend() {
		return frontend;
	}
}
