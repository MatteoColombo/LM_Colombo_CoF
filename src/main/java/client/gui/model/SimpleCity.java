package client.gui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import server.model.board.city.City;
import server.model.board.city.CityConnection;
import server.model.reward.Bonus;
import server.model.reward.Reward;
import util.ColorConverter;

public class SimpleCity {
	
	private static Map<String, String> cityImages;
	static {
		cityImages = new HashMap<>();
		cityImages.put("#ee82ee", "/cities/capital.png"); 
		cityImages.put("#2268df", "/cities/city-blue.png");
		cityImages.put("#f44343", "/cities/city-bronze.png");
		cityImages.put("#ffd700", "/cities/city-gold.png");
		cityImages.put("#008000", "/cities/city-silver.png"); 
	}
	
	private BooleanProperty hasKing;
	private String name;
	private String color;

	// List containing the player's name who have an emporium here
	private ObservableList<Color> emporiums;
	private List<SimpleBonus> bonuses;
	private List<String> connections;
	
	/**
	 * Create a new SimpleCity
	 * @param city the city to unwrap
	 */
	public SimpleCity(City city) {
		this.name = city.getName();
		if(city.isCapital()) {
			this.hasKing = new SimpleBooleanProperty(true);
		} else {
			this.hasKing = new SimpleBooleanProperty(false);
		}
		
		this.connections = new ArrayList<>();
		for(City c: city.getConnectedCities()) {
			this.connections.add(c.getName());
		}
		
		color = ColorConverter.awtToWeb(city.getColor());
		emporiums = FXCollections.observableArrayList();
	}
	
	/**
	 * @return the property saying if the king is here or not
	 */
	public BooleanProperty hasKing() {
		return this.hasKing;
	}
	
	/**
	 * @return the name of the city
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the list of emporiums, as colors
	 */
	public ObservableList<Color> getEmporiums() {
		return this.emporiums;
	}
	
	/**
	 * @return the list of bonuses
	 */
	public List<SimpleBonus> getBonuses() {
		return this.bonuses;
	}
	
	/**
	 * @return the list of connections
	 */
	public List<String> getConnections() {
		return this.connections;
	}
	
	/**
	 * @return the path fot the image of this city
	 */
	public String getImagePath() {
		return cityImages.get(color);
	}
	
	/**
	 * set the Bonuses of this city.
	 * This make sense only in the initialization
	 * @param reward the reward to unwrap
	 */
	public void setBonuses(Reward reward) {
		this.bonuses = new ArrayList<>();
		if(reward != null) {
			for(Bonus b: reward.getGeneratedRewards()) {
				SimpleBonus sb = new SimpleBonus(b);
				bonuses.add(sb);
			}
		}
	}
	
	/**
	 * set the king, if it is here or not
	 */
	public void setHasKing(boolean hasKing){
		this.hasKing.set(hasKing);
	}
	
	/**
	 * set the connections for this city.
	 * This make sense only in the initialization
	 */
	public void setConnections(List<CityConnection> connections){
		this.connections.clear();
		for(CityConnection conn: connections)
			if(this.name.equalsIgnoreCase(conn.getFirstCity()))
				this.connections.add(conn.getSecondCity());
	}
}
