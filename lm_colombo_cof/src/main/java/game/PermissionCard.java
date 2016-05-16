package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class PermissionCard {

	private ArrayList<City> cities;
	/* private Bonus */

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