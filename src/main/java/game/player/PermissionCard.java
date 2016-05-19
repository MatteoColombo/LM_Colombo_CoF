package game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import game.reward.*;

import game.board.City;
/**
 * 
 * @author davidecavallini
 *
 */
public class PermissionCard {

	private ArrayList<City> cities;
	private Reward rewards;
	private boolean used;

	public PermissionCard(List<City> citiesOfRegions) { // Bonus missing
		boolean empty;
		cities = new ArrayList<>();

		// this cycle secure that there is at least one city in the reward
		// TODO there should be a max of it (maybe 3?)

		do {
			for (City x : citiesOfRegions) {
				boolean i = new Random().nextBoolean();
				if (i)
					this.cities.add(x);
			}
			empty = this.cities.isEmpty();
		} while (empty);
	}


	public List<City> getCardCity() {
		return this.cities;
	}

	public Reward getCardBonus() {
		return this.rewards;
	}

	public boolean getIfCardUsed() {
		return this.used;
	}

	public void setCardUsed(){
		this.used = true;
	}

}