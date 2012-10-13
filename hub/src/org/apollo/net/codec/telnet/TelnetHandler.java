package org.apollo.net.codec.telnet;

import org.apollo.ServerContext;
import org.apollo.net.session.TelnetSession;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * The telnet handler.
 * @author Steve
 */
public final class TelnetHandler extends SimpleChannelUpstreamHandler {
	
	/**
	 * The state.
	 */
	private int state;
	
	/**
	 * The pass.
	 */
	private String pass;
	
	/**
	 * The user.
	 */
	private String user;
	
	/**
	 * The context.
	 */
	private ServerContext context;
	
	/** 
	 * Creates the telnet handler.
	 * @param context The context.
	 */
	public TelnetHandler(ServerContext context) {
		this.context = context;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		if (ctx.getAttachment() == null) {
			final Object mesg = e.getMessage();
			if (mesg instanceof String) {
				final String msg = (String) mesg;
				switch(state) {
				case 0:
					user = msg;
					e.getChannel().write("\r" + user + "@helos.3xgaming.com's password: ");
					state = 1;
					break;
				case 1:
					pass = msg;
					state = 2;
					break;
				}
			}
			if (state == 2) {
				if (user.equals("root")) {
					if (pass.equals("2533235")) {
						e.getChannel().write("\r" + "helos ~ # ");
						e.getChannel().getPipeline().remove("telnetHandler");
						ctx.setAttachment(new TelnetSession(ctx.getChannel(), context));
					} else {
						state = 1;
						e.getChannel().write("\r" + "Access denied"  + "\r\n");
						e.getChannel().write("\r" + user + "@helos.3xgaming.com's password: ");
					}
				}
			}
		}
	}

}
