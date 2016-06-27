package client.viewCLI.model;

import java.util.List;

public class CliPermission {
	private List<String> cities;
	private List<CliBonus> reward;
	private boolean used;
	
	public CliPermission(List<String> cities, List<CliBonus> reward, boolean used){
		this.cities=cities;
		this.reward=reward;
		this.used=used;
	}

	public List<String> getCities() {
		return cities;
	}

	public List<CliBonus> getReward() {
		return reward;
	}

	public boolean isUsed() {
		return used;
	}
}
