package model.reward;

import java.io.IOException;

import model.player.Player;

public class BExtraRewardFromCity extends Bonus{
	
	// not really useful here, it may be removed in the future
	public static final int VALUE = 60;
	public static final String NAME = "ccity" ;
	public BExtraRewardFromCity(int amount) {
		super(amount);
	}

	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BExtraRewardFromCity(amount);
	}

	@Override
	public void assignBonusTo(Player p) {
		try {
			p.getClient().askCityToGetNobilityReward(this.getAmount());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
}
