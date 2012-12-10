package org.apollo.game.event.handler.impl;

import org.apollo.game.event.handler.EventHandler;
import org.apollo.game.event.handler.EventHandlerContext;
import org.apollo.game.event.impl.PlayerReportEvent;
import org.apollo.game.model.Player;
import org.apollo.util.NameUtil;

/**
 * An {@link EventHandler} for the {@link PlayerReportEvent}
 * @author Steve
 */
public final class PlayerReportEventHandler extends EventHandler<PlayerReportEvent> {
	
	/**
	 * The names of the reports.
	 */
	private final String[] REPORTS = { "Offensive Language", "Item Scamming", "Password Scamming",
			"Bug abuse", "3xgaming staff impersonation", "Account sharing/trading", "Macroing",
			"Multiple logging in", "Encouraging others to break rules", "Misuse of customer support",
			"Advertising / website", "Real world item trading"};

	@Override
	public void handle(EventHandlerContext ctx, Player player, PlayerReportEvent event) {
		player.sendMessage("Thank you, your report has been recieved.");
		int rule = event.getRule();
		if (rule > REPORTS.length) {
			ctx.breakHandlerChain();
		}
		String victim = NameUtil.decodeBase37(event.getPlayer());
		String report = REPORTS[rule];
		
		System.out.println(victim);
		System.out.println(report);
		System.out.println(Boolean.toString(event.isMutable()));
	}

}
