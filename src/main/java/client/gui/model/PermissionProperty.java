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
	
	public PermissionProperty() {
		used = new SimpleBooleanProperty(false);		
		cityNames = new SimpleStringProperty();
		bonuses = FXCollections.observableArrayList();
	}
	
	public PermissionProperty(PermissionCard perm) {
		this();
		this.set(perm);
	}
	
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
	
	public ObservableList<SimpleBonus> getBonuses() {
		return this.bonuses;
	}
	
	public StringProperty getCities() {
		return this.cityNames;
	}
	
	public BooleanProperty used() {
		return this.used;
	}
}
