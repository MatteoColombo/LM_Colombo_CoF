package game;

public class RewardKing extends Reward{
	
	public RewardKing(int vp) {
		super(new BVictoryPoints(vp));
	}
}
