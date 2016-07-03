package client.cli.model;

import java.util.List;

/**
 * This is the simplified model of the regions, used in the CLI
 * @author Matteo Colombo
 *
 */
public class CliRegion {
	private int regionIndex;
	private List<CliCity> cities;
	private List<String> council;
	private CliPermission[] permission;
	private int bonus;
	
	/**
	 * Instantiates the region
	 * @param index the index in the regions lists
	 * @param cities the cities contained in this region
	 * @param disclosed the number of disclosed permission cards
	 */
	public CliRegion(int index, List<CliCity> cities, int disclosed){
		this.regionIndex=index;
		this.cities=cities;
		this.permission= new CliPermission[disclosed];
	}

	/**
	 * Returns the index of this regione in the regions list
	 * @return an integer
	 */
	public int getRegionIndex() {
		return regionIndex;
	}

	/**
	 * Returns the list of the CliCities in this region
	 * @return the list of CliCities
	 */
	public List<CliCity> getCities() {
		return cities;
	}
	
	/**
	 * Sets the council of this region
	 * @param council the list of councilors
	 */
	public void setCouncil(List<String> council){
		this.council=council;
	}
	
	/**
	 * Returns a list of colors as text
	 * @return the council of the region
	 */
	public List<String> getCouncil(){
		return this.council;
	}
	
	/**
	 * Sets the bonus which is assigned when the region is completed
	 * @param bonus an integer
	 */
	public void setBonus(int bonus){
		this.bonus=bonus;
	}
	
	/**
	 * Returns the bonus which is earned when this region is completed
	 * @return an integer
	 */
	public int getBonus(){
		return this.bonus;
	}
	
	/**
	 * Returns the array of permission cards
	 * @return an array of CliPermission
	 */
	public CliPermission[] getPermission(){
		return permission;
	}
}
