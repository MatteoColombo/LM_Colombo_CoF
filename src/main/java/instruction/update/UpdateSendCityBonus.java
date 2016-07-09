package instruction.update;

import java.util.List;

import client.model.ModelInterface;
import server.model.reward.Reward;

public class UpdateSendCityBonus implements Update {

	private static final long serialVersionUID = 8530863509878061411L;
	private List<Reward> rewards;
	
	public UpdateSendCityBonus(List<Reward> rewards){
		this.rewards=rewards;
	}
	@Override
	public void execute(ModelInterface model) {
		model.setBonus(rewards);
	}

}
