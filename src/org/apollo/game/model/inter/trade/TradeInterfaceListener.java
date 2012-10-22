package org.apollo.game.model.inter.trade;

import org.apollo.game.model.Player;
import org.apollo.game.model.inter.InterfaceAdapter;

/**
 * @author Steve
 */
public final class TradeInterfaceListener extends InterfaceAdapter {

	private final TradeSession session;

	/**
	 * @param session
	 */
	public TradeInterfaceListener(TradeSession session) {
		this.session = session;
	}

	@Override
	public void interfaceClosed(Player player, boolean manually) {
		final TradeSession.State ats = session.getAcquaintance().getTradeSession().getState();
		final TradeSession.State pts = session.getState();
		if (pts != TradeSession.State.DECLINING) {
			if (!(ats == TradeSession.State.AWAITING_ACCEPTANCE && pts == TradeSession.State.AWAITING_ACCEPTANCE)
					|| !(ats == TradeSession.State.AWAITING_COMFORMATION && pts == TradeSession.State.AWAITING_COMFORMATION)) {
				session.decline();
			}
		}
		session.getPlayer().setTradeSession(null);
		session.getAcquaintance().setTradeSession(null);
		session.getAcquaintance().getInterfaceSet().removeListener();
		session.getAcquaintance().getInterfaceSet().close();
	}
}