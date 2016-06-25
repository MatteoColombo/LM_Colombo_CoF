package view.p2pdialogue.notify;

import client.view.ViewInterface;

public class NotifyGameStarted implements Notify {

	private static final long serialVersionUID = 2740943530588613630L;
	
	public NotifyGameStarted() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void execute(ViewInterface view) {
		view.showGame();
	}

}
