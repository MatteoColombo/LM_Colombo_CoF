package game.player;

import game.board.City;

public class Emporium {
	private Player player;
	private City city;

	public Emporium(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return this.player;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
}