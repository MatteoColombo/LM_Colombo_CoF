package game.reward;
/**
 * specialization of the Reward for VictoryPoints only Reward.
 * <p>
 * you can use BVictoryPoints directly, but this provide a more consistent implementation
 * @author Gianpaolo
 */
public class RewardKing extends Reward{
	public RewardKing(int amount) {
		super(new BVictoryPoints(amount));
	}
}
