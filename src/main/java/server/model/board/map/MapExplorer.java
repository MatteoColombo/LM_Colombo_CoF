package server.model.board.map;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import server.model.action.IllegalActionException;
import server.model.board.city.City;
import server.model.board.city.CityConnection;
import server.model.player.Emporium;
import server.model.player.Player;
import server.model.reward.Reward;

/**
 * A class that represent the Map dislocation of the Cities and the connections
 * between each others.
 * <p>
 * The MapExplorer extracts from the Cities which of them is linked to an other
 * one, so it's possible to get the {@link #getDistance(City, City) distance
 * between 2 of them}, retrieve all the {@link #getAdiacentRewards(City, Player)
 * Rewards for adjacent Cities} with an Emporium of the same Player or know if a
 * Player have build an Emporium in {@link #isColorComplete(Player, Color, List)
 * all the Cities of the same Color}.
 *
 * @see City
 * @see CityConnection
 * @see Color
 * @see Distance
 * @see Emporium
 * @see Player
 * @see Reward
 */
public class MapExplorer {
	private List<City> adiacentCities;
	private List<City> connectedCities;

	/**
	 * Initializes the MapExplorer clearing its own lists.
	 * 
	 * @see MapExplorer
	 */
	public void resetAdiacents() {
		this.adiacentCities = new ArrayList<>();
		this.connectedCities = new ArrayList<>();
	}

	/**
	 * Iteratively searches for the {@link City Cities} that have an
	 * {@link Emporium} of the {@link Player} and that are part of a continuous
	 * path.
	 * 
	 * @param startingCity
	 *            the root of the path
	 * @param p
	 *            the Player who owns the Emporiums
	 * @return a list of the Rewards
	 * @see MapExplorer
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
	 * Adds to the list of the {@link City Cities} that have to be visited those
	 * adjacent to the one received.
	 * 
	 * @param root
	 *            the root of the sub path
	 * @see MapExplorer
	 */
	private void addConnectedCities(City root) {
		List<City> adiacentsToRoot = root.getConnectedCities();
		for (City temp : adiacentsToRoot) {
			if (!checkIfInList(temp))
				connectedCities.add(temp);
		}

	}

	/**
	 * Checks if this {@link City} is already in the list of the City which has
	 * to be visited. This is used to avoid loops.
	 * 
	 * @param c
	 *            the City that is going to be checked to control if is already
	 *            in the list
	 * @return <code>true</code> if it is already in the list;
	 *         <code>false</code> otherwise
	 * @see MapExplorer
	 */
	private boolean checkIfInList(City c) {
		for (City temp : connectedCities)
			if (temp.equals(c))
				return true;
		return false;
	}

	/**
	 * Searches for the minimum distance among two {@link City Cities}. It
	 * starts from a root and checks if the adjacent Cities are the desired one,
	 * if true it returns the distance, otherwise it adds them to the list.
	 * Before adding them to the list, it checks if they are already in it. If
	 * no City is found, it throws an exception.
	 * 
	 * @param firstCity
	 *            the initial location
	 * @param secondCity
	 *            the desired location
	 * @return the distance
	 * @see MapExplorer
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
				else if (!inList(distances, connCities.get(j)))
					distances.add(new Distance(connCities.get(j), distances.get(i).getDistance() + 1));
			}

		}
		throw new IllegalActionException("No path found");
	}

	/**
	 * Checks if this {@link City} is already in the list.
	 * 
	 * @param distanceList
	 *            the list of the Cities that have to be checked to find the
	 *            minimal distance
	 * @param c
	 *            the City that is going to be checked to control if is already
	 *            in the list
	 * @return <code>true</code> if it is already in the list;
	 *         <code>false</code> otherwise
	 * @see MapExplorer
	 */
	private boolean inList(List<Distance> distanceList, City c) {
		for (Distance dist : distanceList)
			if (dist.getCity().equals(c))
				return true;
		return false;

	}

	/**
	 * Checks if this {@link Player} has built an {@link Emporium} in every
	 * {@link City} of a determined Color.
	 * 
	 * @param player
	 *            the Player who has built the Emporium
	 * @param cityColor
	 *            the Color of the Cities
	 * @param cities
	 *            the list of all the Cities
	 * @return <code>true</code> if this Player has completed the Color;
	 *         <code>false</code> otherwise
	 * @see MapExplorer
	 */
	public boolean isColorComplete(Player p, Color cityColor, List<City> cities) {
		for (City c : cities)
			if (!c.hasEmporiumOfPlayer(p) && c.getColor().equals(cityColor))
				return false;
		return true;
	}
}
