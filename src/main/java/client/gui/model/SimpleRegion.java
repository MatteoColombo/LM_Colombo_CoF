package client.gui.model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import server.model.board.Region;
import server.model.board.city.City;
import server.model.player.PermissionCard;

public class SimpleRegion {
	private List<SimpleCity> cities;
	private CouncilProperty council;
	private PermissionProperty[] permissions;
	private IntegerProperty conquerBonus;
	
	/**
	 * Create a new SimpleRegion
	 * @param region the region to unwrap
	 */
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
	
	/**
	 * @return the list of cities of this region
	 */
	public List<SimpleCity> getCities() {
		return this.cities;
	}
	
	/**
	 * @return the council of the region
	 */
	public CouncilProperty getCouncil() {
		return this.council;
	}
	
	/**
	 * @return the permission cards
	 */
	public PermissionProperty[] getPermissions() {
		return this.permissions;
	}
	
	/**
	 * @return the amount of points for conquer the region
	 */
	public IntegerProperty conquerBonus() {
		return this.conquerBonus;
	}
	
	/**
	 * Set the counquer value
	 * @param value the amount of victory points
	 */
	public void setCounquerBonus(int value) {
		this.conquerBonus.set(value);
	}
	
	/**
	 * Set a Permission Card
	 * @param permissions the card to unwrap
	 * @param i the slot where yo put the card
	 */
	public void setPermission(PermissionCard permissions, int i) {
		this.permissions[i].set(permissions);
	}
}
