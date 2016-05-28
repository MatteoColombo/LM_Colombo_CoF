package game.reward;

import game.board.Region;

public class BoardRegionReward extends BoardReward {
	private Region bRegionReward;

	public BoardRegionReward(Region rewardKey, int rewardAmount) {
		super(rewardAmount);
		this.bRegionReward = rewardKey;
	}

	public Region getBRKey() {
		return this.bRegionReward;
	}
}