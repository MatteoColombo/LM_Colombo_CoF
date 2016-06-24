package model.reward;
/**
 * specialization of the Reward type. this class take care of the arguments for the random constructor, 
 * in order to generate a balanced reward for a permisison card
 * @author Gianpaolo
 */
public class RewardPermission extends Reward{
	public RewardPermission() {
		super(Bonus.getAllStandardBonus(), 3, 120);
	}
}
