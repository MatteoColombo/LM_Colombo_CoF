package server.model.reward;
/**
 * specialization of the Reward type. this class take care of the arguments for the random constructor, 
 * in order to generate a balanced reward for a nobility step
 * @author Gianpaolo
 */
public class RewardNobility extends Reward{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1108948848681251626L;

	/**
	 * creates a new {@link Reward} for nobility
	 */
	public RewardNobility() {
		super(Bonus.getNobilityBonus(), 2, 80);
	}
}
