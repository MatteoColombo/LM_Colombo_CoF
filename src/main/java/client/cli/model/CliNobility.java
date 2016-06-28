package client.cli.model;

import java.util.List;

public class CliNobility {

	private List<CliBonus> rewards;
	
	public CliNobility(List<CliBonus> rewards){
		this.rewards=rewards;
	}
	
	public List<CliBonus> getReward(){
		return this.rewards;
	}
}
