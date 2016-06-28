package server.control.dialogue.notify;

import client.control.ViewInterface;

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
