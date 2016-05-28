package game.reward;

import java.awt.Color;

public class BoardColorReward extends BoardReward {
	private Color bColorReward;

	public BoardColorReward(Color rewardKey, int rewardAmount) {
		super(rewardAmount);
		this.bColorReward = rewardKey;
	}

	public Color getBRKey() {
		return this.bColorReward;
	}
}