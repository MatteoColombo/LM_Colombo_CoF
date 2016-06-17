package client.model;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.board.city.City;
import model.reward.Bonus;

public class SimpleCity {
	private BooleanProperty hasNoEmporium;
	private BooleanProperty hasKing;
	private StringProperty name;
	/**
	 * List containing the player's name who have an emporium here
	 */
	private ObservableList<StringProperty> emporiums;
	private List<SimpleBonus> bonuses;
	// TODO connections?
	
	public SimpleCity(City city) {
		this.name = new SimpleStringProperty(city.getName());
		
		this.hasNoEmporium = new SimpleBooleanProperty(true);
		if(city.isCapital()) {
			this.hasKing = new SimpleBooleanProperty(true);
		} else {
			this.hasKing = new SimpleBooleanProperty(false);
		}
		
		this.bonuses = FXCollections.observableArrayList();
		for(Bonus b: city.getReward().getGeneratedRewards()) {
			SimpleBonus sb = new SimpleBonus(b.getTagName(), b.getAmount());
			bonuses.add(sb);
		}
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
}
