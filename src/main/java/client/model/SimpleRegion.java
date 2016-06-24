package client.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import model.board.Region;
import model.board.city.City;
import model.player.PermissionCard;

public class SimpleRegion {
	private List<SimpleCity> cities;
	private CouncilProperty council;
	private PermissionProperty[] permissions;
	private IntegerProperty conquerBonus;
	
	public SimpleRegion(Region region) {
		
		cities = new ArrayList<>();
		for(City c: region.getCities()) {
			SimpleCity sc = new SimpleCity(c);
			cities.add(sc);
		}
		
		council = new CouncilProperty();
		council.initCouncil(region.getCouncil().getCouncilorsColor().size());
		
		permissions = new PermissionProperty[region.getPermissionSlotsNumber()];
		for(int i = 0; i < permissions.length; i++) {
			permissions[i] = new PermissionProperty();
		}
		
		conquerBonus = new SimpleIntegerProperty();		
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
	
	public IntegerProperty conquerBonus() {
		return this.conquerBonus;
	}
	
	public void setCounquerBonus(int value) {
		this.conquerBonus.set(value);
	}
	
	public void setPermission(PermissionCard permissions, int i) {
		this.permissions[i].set(permissions);
	}
}
