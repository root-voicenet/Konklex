package org.apollo;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.api.Frontend;
import org.apollo.net.ApiPipelineFactory;
import org.apollo.net.ApolloApiHandler;
import org.apollo.net.ApolloHandler;
import org.apollo.net.TelnetPipelineFactory;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

/**
 * The core class of the Apollo hub server.
 * @author Steve
 */
public final class Server {

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(Server.class.getName());

	/**
	 * The entry point of the Apollo server application.
	 * 
	 * @param args
	 *            The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		Server server = null;
		try {
			server = new Server();
			server.init();
			final SocketAddress telnet = new InetSocketAddress(args.length == 1 ? Integer.parseInt(args[0]) : 3918);
			server.start();
			server.bind(telnet);
		} catch (final Throwable t) {
			logger.log(Level.SEVERE, "Error whilst starting server.", t);
		}
	}

	/**
	 * The {@link ServerBootstrap} for the TELNET listener.
	 */
	private final ServerBootstrap telnetBootstrap = new ServerBootstrap();
	
	/**
	 * The {@link ClientBootstrap} for the servers.
	 */
	private final ClientBootstrap clientBootstrap = new ClientBootstrap();
	
	/**
	 * The server's context.
	 */
	private static ServerContext context;

	/**
	 * The {@link ExecutorService} used for network events. The named thread
	 * factory is unused as Netty names threads itself.
	 */
	private final ExecutorService networkExecutor = Executors
			.newCachedThreadPool();
	
	/**
	 * The service manager.
	 */
	private final ServiceManager serviceManager;

	/**
	 * Creates the Apollo server.
	 * 
	 * @throws Exception
	 *             if an error occurs whilst creating services.
	 */
	public Server() throws Exception {
		logger.info("Starting Apollo...");
		serviceManager = new ServiceManager();
	}

	/**
	 * Binds the server to the specified address.
	 * @param telnetAddress The TELNET address to bind to.
	 */
	public void bind(SocketAddress telnetAddress) {
		logger.info("Binding TELNET listener to address: " + telnetAddress + "...");
		try {
			telnetBootstrap.bind(telnetAddress);
		} catch (final Throwable t) {
			logger.log(Level.WARNING, "Binding to TELNET failed: hub will not work!", t);
		}
		logger.info("Ready for connections.");
	}

	/**
	 * Initialises the server.
	 * @throws Exception Exception initializing this server.
	 */
	public void init() throws Exception {
		context = new ServerContext(new Frontend(), serviceManager, clientBootstrap);
		final ApolloHandler handler = new ApolloHandler(context);
		final ApolloApiHandler apiHandler = new ApolloApiHandler(context);
		final ChannelFactory factory = new NioServerSocketChannelFactory(networkExecutor, networkExecutor);
		final ChannelFactory apiFactory = new NioClientSocketChannelFactory(networkExecutor, networkExecutor);
		telnetBootstrap.setFactory(factory);
		clientBootstrap.setFactory(apiFactory);
		final ChannelPipelineFactory telnetPipelineFactory = new TelnetPipelineFactory(handler, context);
		telnetBootstrap.setPipelineFactory(telnetPipelineFactory);
		final ChannelPipelineFactory clientPipelineFactory = new ApiPipelineFactory(apiHandler, context);
		clientBootstrap.setPipelineFactory(clientPipelineFactory);
	}
	
	/**
	 * Starts the server.
	 * @throws Exception if an error occurs.
	 */
	public void start() throws Exception {
		serviceManager.startAll();
	}
	
	/**
	 * Gets the context.
	 */
	public static final ServerContext getContext() {
		return context;
	}
}
