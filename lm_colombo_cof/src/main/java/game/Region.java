package game;

import java.util.List;

public class Region {
	private final int NUM_CARDS = 2;
	private String regionName;
	private List<City> cities;
	private Council regionCouncil;
	private PermissionCard[] permissionCards;

	public Region(String regionName, List<City> cities, Council regionCouncil) {
		this.regionCouncil = regionCouncil;
		this.cities = cities;
		this.regionCouncil = regionCouncil;
		permissionCards = new PermissionCard[NUM_CARDS];
		generatePermissionCard(0);
		generatePermissionCard(1);
	}

	@Override
	public String toString() {
		return regionName;
	}

	// I need the Player class
	public boolean isCompleted() {
		for (City c : cities) {
			// I need to pass the player
			if (!c.hasEmporiumOfPlayer())
				return false;
		}
		return true;
	}

	// I need the permission cards
	private void generatePermissionCard(int posArray) {

	}

	public PermissionCard getPermissionCard(int posArray) {
		return permissionCards[posArray];
	}
	
	public PermissionCard givePermissionCard(int posArray){
		PermissionCard tempCard= permissionCards[posArray];
		generatePermissionCard(posArray);
		return tempCard;
	}

	public List<City> getCities() {
		return cities;
	}

	public Council getCouncil() {
		return regionCouncil;
	}
	
	public City getCity(String cityName){
		for(City tempCity: cities){
			if(tempCity.getName().equals(cityName))
				return tempCity;
		}
		return null;
	}
	
}

