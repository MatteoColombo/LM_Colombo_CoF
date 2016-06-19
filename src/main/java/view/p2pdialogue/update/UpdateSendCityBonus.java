package view.p2pdialogue.update;

import java.util.List;

import client.model.GameProperty;
import model.reward.Reward;

public class UpdateSendCityBonus implements Update {

	private static final long serialVersionUID = 8530863509878061411L;
	private List<Reward> rewards;
	
	public UpdateSendCityBonus(List<Reward> rewards){
		this.rewards=rewards;
	}
	@Override
	public void execute(GameProperty model) {
		model.getMap().setCityRewards(rewards);
	}

}
