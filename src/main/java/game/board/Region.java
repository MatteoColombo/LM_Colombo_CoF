package game.board;

import java.util.ArrayList;
import java.util.List;
import game.player.*;

public class Region {
	private final int NUM_CARDS;
	private String regionName;
	private List<City> cities;
	private Council regionCouncil;
	private PermissionCard[] permissionCards;
	public Region(){
		this.NUM_CARDS=2;
	}
	
	public Region(String regionName, List<City> cities, Council regionCouncil, int numDisclosedPermCards) {
		this.NUM_CARDS = numDisclosedPermCards;
		this.regionCouncil = regionCouncil;
		this.cities = cities;
		this.regionCouncil = regionCouncil;
		permissionCards = new PermissionCard[NUM_CARDS];
		for (int i = 0; i < permissionCards.length; i++)
			generatePermissionCard(i);
	}

	@Override
	public String toString() {
		return regionName;
	}

	public boolean isCompleted(Player p) {
		for (City c : cities) {
			if (!c.hasEmporiumOfPlayer(p))
				return false;
		}
		return true;
	}

	private void generatePermissionCard(int posArray) {
		ArrayList<String> citiesToString= new ArrayList<>();
		for(City c: cities)
			citiesToString.add(c.getName());
		permissionCards[posArray] = new PermissionCard(citiesToString);
	}

	public PermissionCard getPermissionCard(int posArray) {
		return permissionCards[posArray];
	}

	public PermissionCard givePermissionCard(int posArray) {
		PermissionCard tempCard = permissionCards[posArray];
		generatePermissionCard(posArray);
		return tempCard;
	}

	public List<City> getCities() {
		return cities;
	}

	public Council getCouncil() {
		return regionCouncil;
	}

	public City getCity(String cityName) {
		for (City tempCity : cities) {
			if (tempCity.getName().equals(cityName))
				return tempCity;
		}
		return null;
	}

}
