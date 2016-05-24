package game.reward;

public class RewardKing extends Reward{
	public RewardKing(int amount) {
		super(new BVictoryPoints(amount));
	}
}
