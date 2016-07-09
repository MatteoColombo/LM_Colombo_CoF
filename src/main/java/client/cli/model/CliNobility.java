package client.cli.model;

import java.util.List;

/**
 * This is the Simple version of a nobility track cell
 *
 */
public class CliNobility {

	private List<CliBonus> rewards;
	
	/**
	 * Sets the rewards of the cell
	 * @param rewards
	 */
	public CliNobility(List<CliBonus> rewards){
		this.rewards=rewards;
	}
	
	/**
	 * 
	 * @return the list of CLI bonus reward of the cell
	 */
	public List<CliBonus> getReward(){
		return this.rewards;
	}
}
