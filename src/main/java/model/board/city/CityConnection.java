package model.board.city;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represent the connections between the Cities.
 * <p>
 * {@link #getConnection() Each of them} have a {@link #getFirstCity() beginning
 * City} and an {@link #getSecondCity() arrival one}.
 * 
 * @author Matteo Colombo
 * @see City
 */
public class CityConnection {
	private List<String> cities;

	/**
	 * Initializes the CityConnection class with the two {@link City Cities}.
	 * 
	 * @param firstCity
	 *            the name of the first City
	 * @param secondCity
	 *            the name of the second City
	 * @see CityConnection
	 */
	public CityConnection(String firstCity, String secondCity) {
		this.cities = new ArrayList<>();
		cities.add(firstCity);
		cities.add(secondCity);
	}

	/**
	 * Return the list of the names of the two connected {@link City Cities}.
	 * 
	 * @return the names of the two connected Cities in a list
	 * @see CityConnection
	 */
	public List<String> getConnection() {
		return cities;
	}

	/**
	 * Returns the name of the first {@link City}.
	 * 
	 * @return the name of the first City
	 * @see CityConnection
	 */
	public String getFirstCity() {
		return cities.get(0);
	}

	/**
	 * Returns the name of the second {@link City}.
	 * 
	 * @return the name of the second City
	 * @see CityConnection
	 */
	public String getSecondCity() {
		return cities.get(1);
	}
}
