package game.board;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import game.player.*;
import game.reward.*;

public class City {
	private String cityName;
	private Color cityColor;
	private Reward cityReward;
	private List<Emporium> emporiums;
	private final boolean capital;

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
	}

	/**
	 * This is the constructor which is used to initialize a non-capital city It
	 * initializes the list of the emporiums
	 * @param cityColor a Color, the color of the city
	 * @param cityName a string, the name of the city
	 * @param cityReward a Reward, the prices you win when you place an emporium in the city
	 * @param capital a boolean, true if this city is the capital 
	 */
	public City(Color cityColor, String cityName, Reward cityReward, boolean capital) {
		this.cityName = cityName;
		this.cityColor = cityColor;
		this.emporiums = new ArrayList<>();
		this.cityReward = cityReward;
		this.capital = capital;
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

}
