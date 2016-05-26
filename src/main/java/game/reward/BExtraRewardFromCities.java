package game.reward;

import game.player.Player;

public class BExtraRewardFromCities extends Bonus{
	
	// not really useful here, it may be removed in the future
	public static final int VALUE = 60;
	public static final String NAME = "choice";
	public BExtraRewardFromCities(int amount) {
		super(amount);
	}

	@Override
	public int getValue() {
		return VALUE;
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BExtraRewardFromCities(amount);
	}

	@Override
	public void assignBonusTo(Player p) {
		// TODO ask the player for cities
	}
	
	@Override
	public String getTagName() {
		return NAME;
	}
}
