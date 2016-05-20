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

	public List<Reward> getAdiacentRewards(City startingCity, Player p) {
		resetAdiacents();
		List<Reward> rewards = new ArrayList<>();
		if (!startingCity.isCapital())
			rewards.add(startingCity.getReward());
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

	private void addConnectedCities(City root) {
		List<City> adiacentsToRoot = root.getConnectedCities();
		for (City temp : adiacentsToRoot) {
			if (!checkIfInList(temp))
				connectedCities.add(temp);
		}

	}

	private boolean checkIfInList(City c){
		for(City temp: connectedCities)
			if(temp.equals(c))
				return true;
		return false;
	}
}
