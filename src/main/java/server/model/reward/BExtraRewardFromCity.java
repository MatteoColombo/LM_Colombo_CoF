package server.model.reward;

import java.io.IOException;

import server.model.configuration.Configuration;
import server.model.configuration.ConfigurationErrorException;
import server.model.player.Player;

public class BExtraRewardFromCity extends Bonus{
	
	private static final long serialVersionUID = 6171860243613906131L;
	// not really useful here, it may be removed in the future
	public static final int VALUE = 60;
	public static final String NAME = "city" ;
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
			Configuration config= new Configuration();
			if(p.getEmporium().size()== config.getInitialEmporiums())
				return;
			if((config.getInitialEmporiums()- p.getEmporium().size())<this.getAmount())
				p.getClient().askCityToGetNobilityReward(config.getInitialEmporiums()- p.getEmporium().size());
			p.getClient().askCityToGetNobilityReward(this.getAmount());
		} catch (IOException | ConfigurationErrorException e) {
			//TODO log
			return;
		}
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
}
