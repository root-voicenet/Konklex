package org.apollo.net.session;

import org.apollo.ServerContext;
import org.apollo.game.command.Command;
import org.apollo.game.command.CommandDispatcher;
import org.jboss.netty.channel.Channel;

/**
 * An http session.
 * @author Steve
 */
public final class TelnetSession extends Session {

	/**
	 * The server context.
	 */
	private final ServerContext context;

	/**
	 * Creates an update session for the specified channel.
	 * @param channel The channel.
	 * @param context The server context.
	 */
	public TelnetSession(Channel channel, ServerContext context) {
		super(channel);
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.session.Session#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		// TODO implement
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.net.session.Session#messageReceived(java.lang.Object)
	 */
	@Override
	public void messageReceived(Object message) throws Exception {
		final String str = (String) message;
		final String[] components = str.split(" ");
		final String name = components[0];
		final String[] arguments = new String[components.length - 1];
		System.arraycopy(components, 1, arguments, 0, arguments.length);
		final Command command = new Command(name, arguments);
		CommandDispatcher.getInstance().dispatch(getChannel(), command, context);
		if (getChannel().isConnected() && getChannel().isWritable())
			getChannel().write("konklex> ");
	}
}
