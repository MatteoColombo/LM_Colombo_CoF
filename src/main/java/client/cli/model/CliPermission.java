package client.cli.model;

import java.util.List;
/**
 * This is the simplified model of the Permission card which is used in the CLI
 * @author Matteo Colombo
 *
 */
public class CliPermission {
	private List<String> cities;
	private List<CliBonus> reward;
	private boolean used;

	/**
	 * Initializes the permission card
	 * @param cities the list of the cities
	 * @param reward the list of the rewards
	 * @param used if this card was already used
	 */
	public CliPermission(List<String> cities, List<CliBonus> reward, boolean used) {
		this.cities = cities;
		this.reward = reward;
		this.used = used;
	}

	/**
	 * 
	 * @return returns the list of the cities in which you can build an emporium
	 *         with this card
	 */
	public List<String> getCities() {
		return cities;
	}

	/**
	 * Returns the reward of the permission card
	 * 
	 * @return returns the list of bonus
	 */
	public List<CliBonus> getReward() {
		return reward;
	}

	/**
	 * 
	 * @return true if it was used, false otherwise
	 */
	public boolean isUsed() {
		return used;
	}
}
