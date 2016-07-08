package server.model.reward;

import server.model.board.city.City;

/**
 * A contextual Reward randomly generated.
 * <p>
 * This one is designated specifically for a City.
 * 
 * @author Gianpaolo
 * @see Bonus
 * @see City
 * @see Reward
 */
public class RewardCity extends Reward {

	private static final long serialVersionUID = 8328602223482028431L;

	/**
	 * Creates a new {@link Reward} for {@link City Cities}
	 * 
	 * @see RewardCity
	 */
	public RewardCity() {
		super(Bonus.getCityBonus(), 2, 50);
	}
}
