package model.board.city;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.board.Region;
import model.player.*;
import model.reward.*;
/**
 * The class of the cities.
 * Each city has a color and a name
 * It holds the reward and keep track of the connections with the other cities
 * Emporium of the players are saved in a list
 * @author Matteo Colombo
 *
 */
public class City {
	
	private String cityName;
	private Color cityColor;
	private Reward cityReward;
	private List<Emporium> emporiums;
	private final boolean capital;
	private List<City> connectedCities;
	private Region cityRegion;

	/**
	 * This is the constructor which is used to initialize a non-capital city It
	 * initializes the list of the emporiums
	 * @param cityColor a Color, the color of the city
	 * @param cityName a string, the name of the city
	 * @param cityReward a Reward, the prices you win when you place an emporium in the city
	 */
	public City(Color cityColor, String cityName, Reward cityReward) {
		this.cityName = cityName;
		this.cityColor = cityColor;
		this.emporiums = new ArrayList<>();
		this.cityReward = cityReward;
		this.capital = false;
		this.connectedCities = new ArrayList<>();
	}

	/**
	 * This is the constructor which is used to initialize the capital city It
	 * initializes the list of the emporiums
	 * @param cityColor a Color, the color of the city
	 * @param cityName a string, the name of the city
	 * @param cityReward a Reward, the prices you win when you place an emporium in the city
	 * @param capital a boolean, true if this city is the capital 
	 */
	public City(Color cityColor, String cityName) {
		this.cityName = cityName;
		this.cityColor = cityColor;
		this.emporiums = new ArrayList<>();
		this.cityReward = null;
		this.capital = true;
		this.connectedCities = new ArrayList<>();
	}
	
	/**
	 * Checks if the city is the capital
	 * @return true if capital, false otherwise
	 */
	public boolean isCapital() {
		return capital;
	}

	/**
	 * Returns the name of the city
	 * @return a string
	 */
	public String getName() {
		return cityName;
	}

	/**
	 * Returns the color of the city
	 * @return a color
	 */
	public Color getColor() {
		return cityColor;
	}

	/**
	 * Adds a specified emporium to the list of the emporiums
	 * @param e the emporium that has to be added
	 */
	public void addEmporium(Emporium e) {
		this.emporiums.add(e);
	}

	/**
	 * Checks if the city already has an emporium of the specified player
	 * @param p the player of which we have to check if already has an emporium
	 * @return true if the player already has an emporium, false otherwise
	 */
	public boolean hasEmporiumOfPlayer(Player p) {
		for (Emporium e : emporiums) {
			if (p.equals(e.getPlayer()))
				return true;
		}
		return false;
	}

	/**
	 * Returns the reward that the city is holding
	 * @return a reward
	 */
	public Reward getReward() {
		return cityReward;
	}

	/**
	 * Returns the number of the emporium placed in the city
	 * @return an integer
	 */
	public int getNumberOfEmporium() {
		return this.emporiums.size();
	}
	
	/**
	 * Add a city to the list of the connected cities
	 * @param connection the city that has to be connected
	 */
	public void addConnection(City connection){
		this.connectedCities.add(connection);
	}
	
	
	/**
	 * Checks if the city is connnected to the one which receives as parameter
	 * @param c the city to be checked
	 * @return true if it's connected, false otherwise
	 */
	public boolean isConnectedTo(City c){
		for(City temp: connectedCities)
			if(c.equals(temp))
				return true;
		return false;
	}
	
	
	/**
	 * Returns the list of the connected cities with distance 1
	 * @return a list of cities
	 */
	public List<City> getConnectedCities(){
		return connectedCities;
	}
	
	/**
	 * Sets the reference the the region in which the city is contained
	 * @param cityRegion
	 */
	public void setRegion(Region cityRegion){
		this.cityRegion=cityRegion;
	}
	
	/**
	 * Returns the region in which the city is located
	 * @return a region
	 */
	public Region getRegion(){
		return cityRegion;
	}
	
	

}
