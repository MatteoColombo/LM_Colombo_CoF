package server.control.instruction.update;

import java.util.List;

import client.model.ModelInterface;
import server.model.reward.Reward;

/**
 * Update sent during the gaming setup, setting all the randomly generated city Rewards
 */
public class UpdateSendCityRewards implements Update {

	private static final long serialVersionUID = 8530863509878061411L;
	private List<Reward> rewards;
	
	/**
	 * Create a new UpdateSendCityRewards
	 * @param rewards the list of rewards to apply to the cities
	 */
	public UpdateSendCityRewards(List<Reward> rewards){
		this.rewards=rewards;
	}
	@Override
	public void execute(ModelInterface model) {
		model.setBonus(rewards);
	}

}
