package server.control.dialogue.notify;

import client.control.ViewInterface;

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
