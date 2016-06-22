package client.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import model.board.Region;
import model.board.city.City;
import model.player.PermissionCard;

public class SimpleRegion {
	private List<SimpleCity> cities;
	private CouncilProperty council;
	private PermissionProperty[] permissions;
	private int conquerBonus = 0;
	private BooleanProperty notTaken;
	
	public SimpleRegion(Region region) {
		
		cities = new ArrayList<>();
		for(City c: region.getCities()) {
			SimpleCity sc = new SimpleCity(c);
			cities.add(sc);
		}
		
		council = new CouncilProperty();
		
		permissions = new PermissionProperty[region.getPermissionSlotsNumber()];
		for(int i = 0; i < permissions.length; i++) {
			permissions[i] = new PermissionProperty(region.getPermissionCard(i));
		}
		
		notTaken = new SimpleBooleanProperty(true);
	}
	
	public List<SimpleCity> getCities() {
		return this.cities;
	}
	
	public CouncilProperty getCouncil() {
		return this.council;
	}
	
	public PermissionProperty[] getPermissions() {
		return this.permissions;
	}
	
	public BooleanProperty notTaken() {
		return this.notTaken;
	}
	
	public int getConquerBonus() {
		return this.conquerBonus;
	}
	
	public void setCounquerBonus(int value) {
		this.conquerBonus = value;
	}
	
	public void setPermissions(PermissionCard[] permissions) {
		for(int i = 0; i < this.permissions.length; i++) {
			this.permissions[i] = new PermissionProperty(permissions[i]);
		}
	}
}
