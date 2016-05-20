package game.player;

import game.board.city.City;

public class Emporium {
	private Player player;
	private City city;

	public Emporium(Player player) {
		this.player = player;
		this.city = null;
	}

	public Player getPlayer() {
		return this.player;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public City getCity() {
		return this.city;
	}
}