package client.viewCLI.model;

import java.util.List;

public class CliRegion {
	private int regionIndex;
	private List<CliCity> cities;
	private List<String> council;
	private CliPermission[] permission;
	private int bonus;
	
	public CliRegion(int index, List<CliCity> cities, int disclosed){
		this.regionIndex=index;
		this.cities=cities;
		this.permission= new CliPermission[disclosed];
	}

	public int getRegionIndex() {
		return regionIndex;
	}

	public List<CliCity> getCities() {
		return cities;
	}
	
	public void setCouncil(List<String> council){
		this.council=council;
	}
	
	public List<String> getCouncil(){
		return this.council;
	}
	
	public void setBonus(int bonus){
		this.bonus=bonus;
	}
	
	public int getBonus(){
		return this.bonus;
	}
	
	public CliPermission[] getPermission(){
		return permission;
	}
}
