package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class PermissionCard {

	private ArrayList<City> cities; //still pending if is better to implement that City ArrayList with its own class or with the String one
	/* private Bonus */
	private boolean used;

	public PermissionCard(ArrayList<String> citiesOfRegions) {//Bonus missing
		for (String x : citiesOfRegions) {
			boolean i = new Random().nextBoolean();
			if (i = true) {
				this.cities.add(x);
			}
		}
	}

	public ArrayList<City> getCardCity() {
		return this.cities;
	}

	/*
	 * public Bonus getCardBonus(){ 
	 * return this.Bonus
	 * }
	 */

}