package game.player;

import game.board.city.City;
/**
 * 
 * @author Davide_Cavallini
 *
 */
public class Emporium {
	private Player player;
/**
 * 
 * @param player
 */
	public Emporium(Player player) {
		this.player = player;
	}
/**
 * 
 * @return
 */
	public Player getPlayer() {
		return this.player;
	}

}