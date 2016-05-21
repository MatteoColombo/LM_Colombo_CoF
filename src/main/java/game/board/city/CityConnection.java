package game.board.city;

import java.util.ArrayList;
import java.util.List;

/**
 * A support class used to created the map
 * @author Matteo Colombo
 *
 */
public class CityConnection {
	private List<String> cities;
	
	/**
	 * The constructor of a road among two cities
	 * @param firstCity the name of the first city
	 * @param secondCity the name of the second city
	 */
	public CityConnection(String firstCity, String secondCity){
		this.cities= new ArrayList<>();
		cities.add(firstCity);
		cities.add(secondCity);
	}
	
	/**
	 * Return the list of the names of the two connected cities
	 * @return a List
	 */
	public List<String> getConnection(){
		return cities;
	}
	
	/**
	 * Returns the name of the first city
	 * @return a string
	 */
	public String getFirstCity(){
		return cities.get(0);
	}
	
	/**
	 * Returns the name of the second city
	 * @return a string
	 */
	public String getSecondCity(){
		return cities.get(1);
	}
}
