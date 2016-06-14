package model.board.map;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

import model.board.city.City;
import model.exceptions.IllegalActionException;
import model.player.Player;
import model.reward.Reward;

public class MapExplorer {
	private List<City> adiacentCities;
	private List<City> connectedCities;

	public void resetAdiacents() {
		this.adiacentCities = new ArrayList<>();
		this.connectedCities = new ArrayList<>();
	}

	/**
	 * Iteratively searches for the cities that have an emporium of the player
	 * and that are part of a continous path
	 * 
	 * @param startingCity
	 *            the root of the path
	 * @param p
	 *            the player who owns the emporiums
	 * @return a list of the rewards
	 */
	public List<Reward> getAdiacentRewards(City startingCity, Player p) {
		resetAdiacents();
		List<Reward> rewards = new ArrayList<>();
		connectedCities.add(startingCity);
		for (int i = 0; i < connectedCities.size(); i++) {
			if (connectedCities.get(i).hasEmporiumOfPlayer(p)) {
				adiacentCities.add(connectedCities.get(i));
				if (!connectedCities.get(i).isCapital())
					rewards.add(connectedCities.get(i).getReward());
				addConnectedCities(connectedCities.get(i));
			}
		}
		return rewards;
	}

	/**
	 * Adds to the list of the cities that have to be visited those adiacent to
	 * the one received
	 * 
	 * @param root
	 *            the root of the sub path
	 */
	private void addConnectedCities(City root) {
		List<City> adiacentsToRoot = root.getConnectedCities();
		for (City temp : adiacentsToRoot) {
			if (!checkIfInList(temp))
				connectedCities.add(temp);
		}

	}

	/**
	 * Checks if the city received as parameter is already in the list of the
	 * city which has to be visited. This is used to avoid loops
	 * 
	 * @param c
	 * @return true if it is already in the list, false otherwise
	 */
	private boolean checkIfInList(City c) {
		for (City temp : connectedCities)
			if (temp.equals(c))
				return true;
		return false;
	}

	/**
	 * Searches for the minimum distance among two cities. It starts from a root
	 * and checks if the adiacent cities are the desidered one, if true it
	 * returns the distance, otherwise it adds them to the list. Before adding
	 * them to the list, it checks if they are already in it. If no city is
	 * found it throws an exception.
	 * 
	 * @param firstCity the current location of the king
	 * @param secondCity the desidered location of the king
	 * @return an integer, the distance
	 */
	public int getDistance(City firstCity, City secondCity) throws IllegalActionException {
		if (firstCity.equals(secondCity))
			return 0;
		List<Distance> distances = new ArrayList<>();
		distances.add(new Distance(firstCity, 0));
		for (int i = 0; i < distances.size(); i++) {
			List<City> connCities = distances.get(i).getCity().getConnectedCities();
			for (int j = 0; j < connCities.size(); j++) {
				if (connCities.get(j).equals(secondCity))
					return distances.get(i).getDistance() + 1;
				else if(!inList(distances, connCities.get(j)))
					distances.add(new Distance(connCities.get(j), distances.get(i).getDistance() + 1));
			}

		}
		throw new IllegalActionException("No path found");
	}
	
	/**
	 * Checks if city is already in the list
	 * @param distanceList the list of the cities that have to be checked to find the minimal distance
	 * @param c the city that we have to control if is already in the list
	 * @return true if it's already in the list, false otherwise
	 */
	private boolean inList(List<Distance> distanceList, City c){
		for(Distance dist: distanceList)
			if(dist.getCity().equals(c))
				return true;
		return false;
		
	}
	
	/**
	 * Checks if the player has built an emporium in every city of a determined color
	 * @param player the player who built the emporium
	 * @param cityColor the color of the cities
	 * @param cities the list of all the cities
	 * @return true if the player completed the color, false otherwise
	 */
	public boolean isColorComplete(Player p, Color cityColor, List<City> cities){
		for(City c: cities)
			if(!c.hasEmporiumOfPlayer(p) && c.getColor().equals(cityColor))
				return false;
		return true;
	}
}
