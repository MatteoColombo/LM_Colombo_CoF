package game.reward;

public class BoardReward {
	private String bRName;
	private BVictoryPoints bRBonus;

	public BoardReward(String rewardName, int rewardAmount) {
		this.bRName = rewardName;
		this.bRBonus = new BVictoryPoints(rewardAmount);
	}

	public String getBRName() {
		return this.bRName;
	}

	public BVictoryPoints getBRBonus() {
		return this.bRBonus;
	}
}