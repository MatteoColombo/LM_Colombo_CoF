package client.gui.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.model.board.city.City;
import server.model.player.PermissionCard;
import server.model.reward.Bonus;

public class PermissionProperty {
	private BooleanProperty used;
	private ObservableList<SimpleBonus> bonuses;
	private StringProperty cityNames;
	
	/**
	 * create a new empty PermissionProperty
	 */
	public PermissionProperty() {
		used = new SimpleBooleanProperty(false);		
		cityNames = new SimpleStringProperty();
		bonuses = FXCollections.observableArrayList();
	}
	
	/**
	 * create a new PermissionProperty
	 * @param perm the given permission card for unwrap data
	 */
	public PermissionProperty(PermissionCard perm) {
		this();
		this.set(perm);
	}
	
	/**
	 * set all the attributes
	 * @param perm the given permission card for unwrap data
	 */
	public void set(PermissionCard perm) {
		String buffer = "";
		for(City c: perm.getCardCity()) {
			buffer += "/" + c.getName().substring(0, 1).toUpperCase();
		}

		cityNames.set(buffer.substring(1));
		
		bonuses.clear();
		for(Bonus b: perm.getCardReward().getGeneratedRewards()) {

			bonuses.add(new SimpleBonus(b));
		}
		
		used.set(perm.getIfCardUsed());
	}
	
	/**
	 * @return the bonuses of the card
	 */
	public ObservableList<SimpleBonus> getBonuses() {
		return this.bonuses;
	}
	
	/**
	 * @return the cities of the card
	 */
	public StringProperty getCities() {
		return this.cityNames;
	}
	
	/**
	 * @return if the card is used or not
	 */
	public BooleanProperty used() {
		return this.used;
	}
}
