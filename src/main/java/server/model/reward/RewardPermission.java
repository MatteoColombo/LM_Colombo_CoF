package server.model.reward;
/**
 * specialization of the Reward type. this class take care of the arguments for the random constructor, 
 * in order to generate a balanced reward for a permisison card
 * @author Gianpaolo
 */
public class RewardPermission extends Reward{
	
	private static final long serialVersionUID = -5265241063177120154L;
	
	/**
	 * creates a new {@link Reward} for permissions
	 */
	public RewardPermission() {
		super(Bonus.getAllStandardBonus(), 3, 120);
	}
}
