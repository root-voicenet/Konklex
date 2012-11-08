package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PlayerReportEvent;
import org.apollo.game.model.Player;
import org.apollo.game.model.Player.PrivilegeLevel;
import org.apollo.util.NameUtil;

/**
 * An {@link EventHandler} for the {@link PlayerReportEvent}
 * @author Steve
 */
public final class PlayerReportEventHandler extends EventHandler<PlayerReportEvent> {

	private static final String[] REPORT_NAMES = { "Offensive language", "Item scamming", "Password scamming",
		"Bug abuse", "Staff impersonation", "Other", "Macroing", "Duping", "Encouraging others to break the rules",
		"Yell abuse", "Advertising", "Possible duped items" };

	@SuppressWarnings("unused")
	@Override
	public void handle(EventHandlerContext ctx, Player player, PlayerReportEvent event) {
		final int rule = event.getRule();
		if (rule < 0 || rule > REPORT_NAMES.length) {
			ctx.breakHandlerChain();
		}

		final String report = REPORT_NAMES[rule];
		final String victim = NameUtil.decodeBase37(event.getPlayer());

		if (event.isMutable() && player.getPrivilegeLevel().toInteger() >= PrivilegeLevel.MODERATOR.toInteger()) {
			// We can mute the player.
		}
		else {
			// Mute is either false xor privilege level is invalid.
		}
	}

}
