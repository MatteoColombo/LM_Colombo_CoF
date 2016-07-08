package server.model.reward;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.board.city.City;
import server.model.board.nobility.NobilityTrack;
import server.model.player.Nobility;
import server.model.player.Player;

/**
 * A Bonus that assigns to a Player the Rewards from a City he owns, except if
 * there are Nobility ones between them.
 * <p>
 * This is a Bonus of the NobilityTrack.
 * 
 * @see Bonus
 * @see City
 * @see Nobility
 * @see NobilityTrack
 * @see Player
 * @see Reward
 */
public class BExtraRewardFromCity extends Bonus {
	private static final Logger log = Logger.getLogger(BExtraRewardFromCity.class.getName());
	private static final long serialVersionUID = 6171860243613906131L;
	// not really useful here, it may be removed in the future
	public static final int VALUE = 60;
	public static final String NAME = "city";

	/**
	 * Sets the number of {@link City Cities} that can be chosen.
	 * 
	 * @param amount
	 *            the number of Cities that will be chosen
	 * @see BExtraRewardFromCity
	 */
	public BExtraRewardFromCity(int amount) {
		super(amount);
	}

	@Override
	protected int getValue() {
		return VALUE;
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BExtraRewardFromCity(amount);
	}

	@Override
	public void assignBonusTo(Player p) {
		try {
			Configuration config = new Configuration();
			if (p.getEmporium().size() == config.getInitialEmporiums())
				return;
			if ((config.getInitialEmporiums() - p.getEmporium().size()) < this.getAmount())
				p.getClient().askCityToGetNobilityReward(config.getInitialEmporiums() - p.getEmporium().size());
			p.getClient().askCityToGetNobilityReward(this.getAmount());
		} catch (IOException | ConfigurationErrorException e) {
			log.log(Level.SEVERE, e.toString(), e);
		}
	}

	@Override
	public String getTagName() {
		return NAME;
	}
}
