package game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.board.City;
import game.board.exceptions.NegativeException;
/**
 * 
 * @author davidecavallini
 *
 */
public class PermissionCard {

	private ArrayList<City> cities; // still pending if is better to implement
									// that City ArrayList with its own class or
									// with the String one
	/* private Bonus */
	private boolean used;
	public PermissionCard(List<City> citiesOfRegions) { // Bonus missing
		boolean empty;
		cities = new ArrayList<City>();
		// this cycle secure that there is at least one city in the reward
		// TODO there should be a max of it (maybe 3?)
		do {
			for (City x : citiesOfRegions) {
				boolean i = new Random().nextBoolean();
				if (i == true)
					this.cities.add(x);
			}
			empty = this.cities.isEmpty();
		} while (empty == true);
	}

	public ArrayList<City> getCardCity() {
		return this.cities;
	}

	/*
	 * public Bonus getCardBonus(){
	 * 	return this.Bonus
	 * }
	 */

	public boolean getIfCardUsed() {
		return this.used;
	}

	public void setCardUsed(){
		this.used = true;
	}

}