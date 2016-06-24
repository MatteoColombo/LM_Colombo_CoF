 package client.model;

import java.util.ArrayList;
import java.util.List;

import model.reward.Bonus;
import model.reward.Reward;

public class SimpleNobilityCell {
	private List<SimpleBonus> bonuses;
	
	public SimpleNobilityCell(Reward reward) {
			
		bonuses = new ArrayList<>();
		for(Bonus b: reward.getGeneratedRewards()) {
			SimpleBonus sb = new SimpleBonus(b);
			bonuses.add(sb);
		}
	}
	
	public List<SimpleBonus> getBonuses() {
		return this.bonuses;
	}
}
