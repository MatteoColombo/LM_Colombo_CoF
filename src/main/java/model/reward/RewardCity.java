package model.reward;
/**
 * specialization of the Reward type. this class take care of the arguments for the random constructor,
 * in order to generate a balanced reward for a city
 * @author Gianpaolo
 */
public class RewardCity extends Reward{
	
	private static final long serialVersionUID = 8328602223482028431L;

	public RewardCity() {	
		super(Bonus.getCityBonus(), 2, 50);
	}
}
