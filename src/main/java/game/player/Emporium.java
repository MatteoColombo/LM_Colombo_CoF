package game.player;

import game.board.city.City;
/**
 * 
 * @author Davide_Cavallini
 *
 */
public class Emporium {
	private Player player;
	private City city;
/**
 * 
 * @param player
 */
	public Emporium(Player player) {
		this.player = player;
		this.city = null;
	}
/**
 * 
 * @return
 */
	public Player getPlayer() {
		return this.player;
	}
/**
 * 
 * @param city
 */
	public void setCity(City city) {
		this.city = city;
	}
/**
 * 
 * @return
 */
	public City getCity() {
		return this.city;
	}
}