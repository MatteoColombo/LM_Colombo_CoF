package game;

import server.ClientInt;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<ClientInt> players;
	private boolean complete;
	
	public Game() {
		players = new ArrayList<ClientInt>();
		complete = false;
		// TODO ...where to wait 20 seconds?
	}
	
	public void addPlayer(ClientInt client) {
		players.add(client);
	}

	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}
}
