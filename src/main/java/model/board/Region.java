package model.board;
	
import java.util.List;

import model.board.city.City;
import model.board.council.Council;
import model.player.*;

public class Region {
	private final int NUM_CARDS;
	private String regionName;
	private List<City> cities;
	private Council regionCouncil;
	private PermissionCard[] permissionCards;
	public Region(){
		this.NUM_CARDS=2;
	}
	
	/**
	 * Constructor of the region, it receives the parameters and it initializes the permission cards
	 * @param regionName a string, the name of the region
	 * @param cities a list of the cities located in the region
	 * @param regionCouncil a council, the council of the region
	 * @param numDisclosedPermCards an integer, the number of disclosed permit cards
	 * @see City
	 * @see Councul
	 */
	public Region(String regionName, List<City> cities, Council regionCouncil, int numDisclosedPermCards) {
		this.NUM_CARDS = numDisclosedPermCards;
		this.regionName=regionName;
		this.regionCouncil = regionCouncil;
		this.cities = cities;
		this.regionCouncil = regionCouncil;
		permissionCards = new PermissionCard[NUM_CARDS];
		for (int i = 0; i < permissionCards.length; i++)
			generatePermissionCard(i);
	}

	/**
	 * Returns the name of the city as a string
	 */
	@Override
	public String toString() {
		return regionName;
	}

	/**
	 * Checks if a player has an emporium in each city of the region
	 * @param p the player who has to be checked
	 * @return true if the player completed the region, false otherwise
	 */
	public boolean isCompleted(Player p) {
		for (City c : cities) {
			if (!c.hasEmporiumOfPlayer(p))
				return false;
		}
		return true;
	}

	/**
	 * Generates a permission cards and puts it in the specified position
	 * @param posArray an integer, it specifies in which position the permit card has to be placed
	 */
	private void generatePermissionCard(int posArray) {
		permissionCards[posArray] = new PermissionCard(cities);
	}
	
	/**
	 * generate a new permission card in all the available slots
	 */
	public void shufflePermissionCards() {
		for (int i = 0; i < permissionCards.length; i++) {
			generatePermissionCard(i);
		}
	}
	
	/**
	 * Returns the permission card without removing it from the map. 
	 * Use this if you want to check the permission card in a specified slot
	 * @param posArray an integer, it specifies which permit card you want to see
	 * @return a permission card
	 */
	public PermissionCard getPermissionCard(int posArray) {
		return permissionCards[posArray];
	}
	
	/**
	 * Returns the permission card ad removes it from the map.
	 * The free slot is filled with another permit card.
	 * Use this if a player acquires a permit card
	 * @param posArray an integer, the slot of the permission card that was bought
	 * @return the permission card which was bought
	 */
	public PermissionCard givePermissionCard(int posArray) {
		PermissionCard tempCard = permissionCards[posArray];
		generatePermissionCard(posArray);
		return tempCard;
	}

	/**
	 * Returns the list of the cities located in the region
	 * @return a List of City
	 */
	public List<City> getCities() {
		return cities;
	}

	/**
	 * Returns the council located in the region
	 * @return a Council
	 */
	public Council getCouncil() {
		return regionCouncil;
	}
	
	/**
	 * Searches for a city in the cities list.
	 * @param cityName a string, the name of the desidered city
	 * @return the city, null if it doesn't exists
	 */
	public City getCity(String cityName) {
		for (City tempCity : cities) {
			if (tempCity.getName().equals(cityName))
				return tempCity;
		}
		return null;
	}
	
	/**
	 * Checks if a player has placed an emporium in each city of the region
	 * @param player the player who placed the emporiums
	 * @return true if the region is complete, false otherwise
	 */
	public boolean isRegionComplete(Player p){
		for(City c: cities)
			if(!c.hasEmporiumOfPlayer(p))
				return false;
		return true;
	}
}
