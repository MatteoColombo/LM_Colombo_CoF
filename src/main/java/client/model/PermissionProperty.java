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
import model.player.PermissionCard;
import model.reward.Bonus;

public class PermissionProperty {
	private BooleanProperty used;
	private ObservableList<SimpleBonus> bonuses;
	private StringProperty cityNames;
	
	public PermissionProperty(PermissionCard permissionCard) {
		used = new SimpleBooleanProperty(false);		
		cityNames = new SimpleStringProperty("");
		bonuses = FXCollections.observableArrayList();
		this.set(permissionCard);
	}
	
	public void set(PermissionCard perm) {
		String buffer = "";
		for(City c: perm.getCardCity()) {
			buffer.concat("/" + c.getName().substring(0, 1).toUpperCase());
		}
		buffer.replaceFirst("/", "");
		cityNames.set(buffer);
		bonuses.clear();
		for(Bonus b: perm.getCardReward().getGeneratedRewards()) {
			bonuses.add(new SimpleBonus(b));
		}
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
