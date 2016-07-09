package server.control.instruction.notify;

import client.control.ViewInterface;
/**
 * Notify that the market selling round started
 */
public class NotifyMarketSellStarted implements Notify {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7532547769748126140L;

	@Override
	public void execute(ViewInterface view) {
		view.showMarket();
	}

}
