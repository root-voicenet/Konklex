package org.apollo.game.model.inter.trade;

import org.apollo.game.model.inter.InterfaceListener;

/**
 * @author Steve
 */
public final class TradeInterfaceListener implements InterfaceListener {

	private final TradeSession session;

	/**
	 * @param session
	 */
	public TradeInterfaceListener(TradeSession session) {
		this.session = session;
	}

	@Override
	public void interfaceClosed() {
		TradeSession.State ats = session.getAcquaintance().getTradeSession().getState();
		TradeSession.State pts = session.getState();
		if (pts != TradeSession.State.DECLINING) {
			if (!(ats == TradeSession.State.AWAITING_ACCEPTANCE && pts == TradeSession.State.AWAITING_ACCEPTANCE)
					|| !(ats == TradeSession.State.AWAITING_COMFORMATION && pts == TradeSession.State.AWAITING_COMFORMATION)) {
				System.out.println("Player: " + session.getPlayer() + ", Acquaintance: " + session.getAcquaintance() + ", State Player: " + pts.toString()
						+ ", State Acquaintance: " + ats.toString());
				session.decline();
			}
		}
		session.getPlayer().setTradeSession(null);
		session.getAcquaintance().setTradeSession(null);
		session.getAcquaintance().getInterfaceSet().removeListener();
		session.getAcquaintance().getInterfaceSet().close();
	}
}