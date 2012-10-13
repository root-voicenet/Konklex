package org.apollo.api.method.handler.impl;

import org.apollo.api.ProxyApiSession;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.ReceiveFriendMethod;
import org.apollo.game.event.impl.SendFriendEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.World;
import org.apollo.util.NameUtil;

/**
 * An {@link MethodHandler} for the {@link ReceiveFriendMethod}
 * @author Steve
 */
public final class ReceiveFriendMethodHandler extends MethodHandler<ReceiveFriendMethod> {

	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, ReceiveFriendMethod method) {
		final String sender = NameUtil.decodeBase37(method.getSender());
		final Player player = World.getWorld().getPlayer(sender);
		System.out.println("Sender: " + sender);
		System.out.println("Friend: " + NameUtil.decodeBase37(method.getFriend()));
		System.out.println("Status: " + method.getStatus());
		if (player != null) {
			player.send(new SendFriendEvent(method.getFriend(), method.getStatus()));
		} else {
			System.out.println("Player is null");
		}
	}

}
