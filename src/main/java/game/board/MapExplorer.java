package game.board;

import java.util.ArrayList;
import java.util.List;

import game.board.city.City;
import game.player.Player;
import game.reward.Reward;

public class MapExplorer {
	private List<City> adiacentCities;
	private List<City> connectedCities;

	public void resetAdiacents() {
		this.adiacentCities = new ArrayList<>();
		this.connectedCities = new ArrayList<>();
	}

	/**
	 * Iteratively searches for the cities that have an emporium of the player and that are part of a continous path
	 * @param startingCity the root of the path
	 * @param p the player who owns the emporiums
	 * @return a list of the rewards
	 */
	public List<Reward> getAdiacentRewards(City startingCity, Player p) {
		resetAdiacents();
		List<Reward> rewards = new ArrayList<>();;
		connectedCities.add(startingCity);
		for (City temp : connectedCities) {
			if (temp.hasEmporiumOfPlayer(p)) {
				adiacentCities.add(temp);
				if (!temp.isCapital())
					rewards.add(temp.getReward());
				addConnectedCities(temp);
			}
		}
		return rewards;
	}

	/**
	 * Adds to the list of the cities that have to be visited those adiacent to the one received
	 * @param root the root of the sub path
	 */
	private void addConnectedCities(City root) {
		List<City> adiacentsToRoot = root.getConnectedCities();
		for (City temp : adiacentsToRoot) {
			if (!checkIfInList(temp))
				connectedCities.add(temp);
		}

	}
	/**
	 * Checks if the city received as parameter is already in the list of the city which has to be visited. 
	 * This is used to avoid loops 
	 * @param c
	 * @return true if it is already in the list, false otherwise
	 */
	private boolean checkIfInList(City c){
		for(City temp: connectedCities)
			if(temp.equals(c))
				return true;
		return false;
	}
}
