package model.board.city;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.action.Action;
import model.board.King;
import model.board.Region;
import model.player.*;
import model.reward.*;

/**
 * A class that represent all of the Cities in the Map.
 * <p>
 * Each City keeps track of the {@link #getConnectedCities() connections with
 * the other ones}, with the possibility of {@link #isConnectedTo(City) checking
 * already existing} or {@link #addConnection(City) adding new ones}. Every City
 * has also:
 * <ul>
 * <li>A {@link #getColor() Color};</li>
 * <li>A {@link #getName() name};</li>
 * <li>A boolean to know if this City {@link #isCapital() is or not the King's
 * capital} ;</li>
 * <li>A {@link #getReward() Reward} that is awarded when a Player has
 * {@link #addEmporium(Emporium) built his Emporium} here with an Action;</li>
 * <li>A {@link #getNumberOfEmporium() number of Emporiums}, each of them with
 * its own {@link #hasEmporiumOfPlayer(Player) Player owner};</li>
 * <li>A {@link #getRegion() reference to the Region} where it is, that have to
 * be {@link #setRegion(Region) set with the Region} itself.</li>
 * </ul>
 * 
 * @author Matteo Colombo
 * @see Action
 * @see CityConnection
 * @see Color
 * @see Emporium
 * @see King
 * @see Player
 * @see Region
 * @see Reward
 */
public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1519838226056409747L;
	private String cityName;
	private Color cityColor;
	private Reward cityReward;
	private transient List<Emporium> emporiums;
	private final boolean capital;
	private transient List<City> connectedCities;
	private transient Region cityRegion;

	/**
	 * Initializes a not-capital City with its name, {@link Color} and
	 * {@link Reward}, creating also the list of its {@link Emporium Emporiums}
	 * and {@link CityConnection CityConnections}.
	 * 
	 * @param cityColor
	 *            the Color of this City
	 * @param cityName
	 *            the name of this City
	 * @param cityReward
	 *            the Reward a Player will receive when he will place here an
	 *            Emporium
	 * @see City
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
	 * Initializes the capital City with its name and {@link Color}, creating
	 * also the list of its {@link Emporium Emporiums} and {@link CityConnection
	 * CityConnections}; no {@link Reward Rewards} are associated with the
	 * capital.
	 * 
	 * @param cityColor
	 *            the Color of this capital City
	 * @param cityName
	 *            the name of this capital City
	 * @see City
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
	 * Returns if this City is the capital.
	 * 
	 * @return <code>true</code> if this City is the capital; <code>false</code>
	 *         otherwise
	 * @see City
	 */
	public boolean isCapital() {
		return capital;
	}

	/**
	 * Returns the name of this City.
	 * 
	 * @return the name of this City
	 */
	public String getName() {
		return cityName;
	}

	/**
	 * Returns the {@link Color} of this City.
	 * 
	 * @return the Color of this City
	 * @see City
	 */
	public Color getColor() {
		return cityColor;
	}

	/**
	 * Adds a specified {@link Emporium} to the list of its Emporiums.
	 * 
	 * @param e
	 *            the Emporium that will be added
	 * @see City
	 */
	public void addEmporium(Emporium e) {
		this.emporiums.add(e);
	}

	/**
	 * Checks if this City already has an {@link Emporium} of this
	 * {@link Player}.
	 * 
	 * @param p
	 *            the Player that will be checked if it already has an Emporium
	 *            here
	 * @return <code>true</code> if this Player already has an Emporium;
	 *         <code>false</code> otherwise
	 * @see City
	 */
	public boolean hasEmporiumOfPlayer(Player p) {
		for (Emporium e : emporiums) {
			if (p.equals(e.getPlayer()))
				return true;
		}
		return false;
	}
	
	/**
	 * Returns the list of the emporiums
	 * @return
	 */
	public List<Emporium> getEmporiums(){
		return emporiums;
	}

	/**
	 * Returns the {@link Reward} that this City is holding.
	 * 
	 * @return the City Reward
	 * @see City
	 */
	public Reward getReward() {
		return cityReward;
	}

	/**
	 * Returns the number of the {@link Emporium Emporiums} placed in this City.
	 * 
	 * @return the number of the Emporiums this City already has
	 * @see City
	 */
	public int getNumberOfEmporium() {
		return this.emporiums.size();
	}

	/**
	 * Adds a City to the list of the {@link CityConnection} of this one.
	 * 
	 * @param connection
	 *            the city that will be connected with this one
	 * @see City
	 */
	public void addConnection(City connection) {
		this.connectedCities.add(connection);
	}

	/**
	 * Checks if a City is connected with this one.
	 * 
	 * @param c
	 *            the City to be checked if it's connected with this one
	 * @return <code>true</code> if it's connected; <code>false</code> otherwise
	 * @see City
	 */
	public boolean isConnectedTo(City c) {
		for (City temp : connectedCities)
			if (c.equals(temp))
				return true;
		return false;
	}

	/**
	 * Returns the list of the adjacent Cities.
	 * 
	 * @return the list of the adjacent Cities
	 * @see City
	 */
	public List<City> getConnectedCities() {
		return connectedCities;
	}

	/**
	 * Sets the reference of the {@link Region} in which this City is contained.
	 * 
	 * @param cityRegion
	 *            the Region that contains this City
	 * @see City
	 */
	public void setRegion(Region cityRegion) {
		this.cityRegion = cityRegion;
	}

	/**
	 * Returns the {@link Region} in which this City is located.
	 * 
	 * @return the Region that contains this City
	 * @see City
	 */
	public Region getRegion() {
		return cityRegion;
	}

}
