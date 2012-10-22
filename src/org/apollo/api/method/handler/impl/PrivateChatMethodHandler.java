package org.apollo.api.method.handler.impl;

import org.apollo.api.ProxyApiSession;
import org.apollo.api.method.handler.MethodHandler;
import org.apollo.api.method.handler.MethodHandlerContext;
import org.apollo.api.method.impl.PrivateChatMethod;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.game.model.World;
import org.apollo.security.PlayerCredentials;
import org.apollo.util.NameUtil;

/**
 * An {@link MethodHandler} for the {@link PrivateChatMethod}.
 * @author Steve
 */
public final class PrivateChatMethodHandler extends MethodHandler<PrivateChatMethod> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apollo.game.event.handler.EventHandler#handle(org.apollo.game.event .handler.EventHandlerContext,
	 * org.apollo.game.model.Player, org.apollo.game.event.Event)
	 */
	@Override
	public void handle(MethodHandlerContext ctx, ProxyApiSession session, PrivateChatMethod method) {
		Player player = new Player(new PlayerCredentials(NameUtil.decodeBase37(method.getSender()), null, 0, 0), null);
		player.setPrivilegeLevel(PrivilegeLevel.valueOf(method.getLevel()));
		World.getWorld().getMessaging().sendPrivateMessage(player, method.getFriend(), method.getMessage());
	}
}