package client.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.board.city.City;
import model.reward.Bonus;
import model.reward.Reward;
import util.ColorConverter;

public class SimpleCity {
	
	
	private BooleanProperty hasNoEmporium;
	private BooleanProperty hasKing;
	private StringProperty name;
	private String color;
	/**
	 * List containing the player's name who have an emporium here
	 */
	private ObservableList<StringProperty> emporiums;
	private List<SimpleBonus> bonuses;
	private List<String> connections;
	
	public SimpleCity(City city) {
		this.name = new SimpleStringProperty(city.getName());
		this.hasNoEmporium = new SimpleBooleanProperty(true);
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
	}
	
	public BooleanProperty hasNoEmporium() {
		return this.hasNoEmporium;
	}
	
	public BooleanProperty hasKing() {
		return this.hasKing;
	}
	
	public StringProperty getName() {
		return this.name;
	}
	
	public List<StringProperty> getEmporiums() {
		return this.emporiums;
	}
	
	public List<SimpleBonus> getBonuses() {
		return this.bonuses;
	}
	
	public List<String> getConnections() {
		return this.connections;
	}
	
	public void setBonuses(Reward reward) {
		this.bonuses = new ArrayList<>();
		if(reward != null) {
			for(Bonus b: reward.getGeneratedRewards()) {
				SimpleBonus sb = new SimpleBonus(b);
				bonuses.add(sb);
			}
		}
	}
}
