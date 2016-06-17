package client.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.board.city.City;
import model.player.PermissionCard;
import model.reward.Bonus;

public class PermissionProperty {
	private BooleanProperty used;
	private List<SimpleBonus> bonuses;
	private List<String> cityNames;
	
	public PermissionProperty(PermissionCard permissionCard) {
		used = new SimpleBooleanProperty(false);
		
		bonuses = new ArrayList<>();
		for(Bonus b: permissionCard.getCardReward().getGeneratedRewards()) {
			SimpleBonus sb = new SimpleBonus(b.getTagName(), b.getAmount());
			bonuses.add(sb);
		}
		
		cityNames = new ArrayList<>();
		for(City c: permissionCard.getCardCity()) {
			cityNames.add(c.getName());
		}
	}
	
	public List<SimpleBonus> getBonuses() {
		return this.bonuses;
	}
	
	public List<String> getCities() {
		return this.cityNames;
	}
	
	public BooleanProperty used() {
		return this.used;
	}
}
