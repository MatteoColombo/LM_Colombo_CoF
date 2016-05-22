package game.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.board.city.City;
import game.reward.*;
/**
 * 
 * @author Davide_Cavallini
 *
 */
public class PermissionCard {

	private List<City> cities;
	private Reward reward;
	private boolean used;
	/**
	 * standard constructor, which assign passed parameter
	 * @param cities
	 * @param reward
	 */
	public PermissionCard(List<City> cities, Reward reward) {
		this.cities = cities;
		this.reward = reward;
	}
	/**
	 * 
	 * @param citiesOfRegions
	 */
	public PermissionCard(List<City> citiesOfRegions) { // Bonus missing
		boolean empty;
		cities = new ArrayList<>();
		Random r = new Random();
		// this cycle secure that there is at least one city in the reward
		// TODO there should be a max of it (maybe 3?)
		do {
			for (City x : citiesOfRegions) {
				boolean i = r.nextBoolean();
				if (i)
					this.cities.add(x);
			}
			empty = this.cities.isEmpty();
		} while (empty);
	
		reward = new RewardPermission();
	}
/**
 * 
 * @return
 */

	public List<City> getCardCity() {
		return this.cities;
	}
/**
 * 
 */
	public Reward getCardReward() {
		return this.reward;
	}
/**
 * 
 * @return
 */
	public boolean getIfCardUsed() {
		return this.used;
	}
/**
 * 
 */
	public void setCardUsed(){
		this.used = true;
	}

}