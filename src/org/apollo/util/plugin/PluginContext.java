package org.apollo.util.plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apollo.ServerContext;
import org.apollo.backend.FrontendService;
import org.apollo.backend.codec.session.FrontendSession;
import org.apollo.backend.method.Method;
import org.apollo.backend.method.handler.MethodHandler;
import org.apollo.backend.method.handler.chain.MethodHandlerChain;
import org.apollo.backend.method.handler.chain.MethodHandlerChainGroup;
import org.apollo.game.GameService;
import org.apollo.game.command.CommandListener;
import org.apollo.game.event.Event;
import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.chain.EventHandlerChain;
import org.apollo.game.event.handler.chain.EventHandlerChainGroup;
import org.apollo.game.model.World;
import org.apollo.net.release.EventDecoder;
import org.apollo.net.release.EventEncoder;
import org.apollo.net.release.Release;

/**
 * The {@link PluginContext} contains methods a plugin can use to interface with
 * the server, for example, by adding {@link EventHandler}s to
 * {@link EventHandlerChain}s.
 * @author Graham
 */
public final class PluginContext {

	/**
	 * The server context.
	 */
	private final ServerContext context;

	/**
	 * The logger for this class.
	 */
	private static final Logger logger = Logger.getLogger(FrontendSession.class.getName());

	/**
	 * Creates the plugin context.
	 * @param context The server context.
	 */
	public PluginContext(ServerContext context) {
		this.context = context;
	}

	/**
	 * Adds a command listener.
	 * @param name The name of the listener.
	 * @param listener The listener.
	 */
	public void addCommandListener(String name, CommandListener listener) {
		World.getWorld().getCommandDispatcher().register(name, listener); // TODO
		// best
		// way?
	}

	/**
	 * Adds an event decoder.
	 * @param <T> The type of decoder.
	 * @param releaseNo The release number.
	 * @param opcode The opcode.
	 * @param decoder The event decoder.
	 */
	public <T extends Event> void addEventDecoder(int releaseNo, int opcode, EventDecoder<T> decoder) {
		final Release release = context.getRelease();
		if (release.getReleaseNumber() != releaseNo)
			return;
		release.register(decoder, opcode);
	}

	/**
	 * Adds an event encoder.
	 * @param <T> The type of encoder.
	 * @param releaseNo The release number.
	 * @param event The event.
	 * @param encoder The event encoder.
	 */
	public <T extends Event> void addEventEncoder(int releaseNo, Class<T> event, EventEncoder<T> encoder) {
		final Release release = context.getRelease();
		if (release.getReleaseNumber() != releaseNo)
			return;
		release.register(event, encoder);
	}

	/**
	 * Adds an event handler to the end of the chain.
	 * @param <T> The type of event.
	 * @param event The event.
	 * @param handler The handler.
	 */
	public <T extends Event> void addLastEventHandler(Class<T> event, EventHandler<T> handler) {
		final EventHandlerChainGroup chains = context.getService(GameService.class).getEventHandlerChains();
		final EventHandlerChain<T> chain = chains.getChain(event);
		if (chain == null)
			logger.log(Level.WARNING, "Chain missing, add " + event.getCanonicalName() + " to events.xml");
		else
			chain.addLast(handler);
	}

	/**
	 * Adds an method handler to the end of the chain.
	 * @param <T> The type of method.
	 * @param event The method.
	 * @param handler The handler.
	 */
	public <T extends Method> void addLastMethodHandler(Class<T> event, MethodHandler<T> handler) {
		if (context.getService(FrontendService.class) != null) {
			final MethodHandlerChainGroup chains = context.getService(FrontendService.class).getMethodHandlerChains();
			final MethodHandlerChain<T> chain = chains.getChain(event);
			if (chain == null)
				logger.log(Level.WARNING, "Chain missing, add " + event.getCanonicalName() + " to methods.xml");
			else
				chain.addLast(handler);
		}
	}
}
