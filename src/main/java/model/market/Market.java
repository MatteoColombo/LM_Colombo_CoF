package model.market;

import java.util.List;

import model.player.*;

public class Market {
	private List<Player> allPlayers;

	public Market(List<Player> allPlayers) {
		this.allPlayers = allPlayers;
	}

	public List<Player> getAllPlayers() {
		return allPlayers;
	}

}