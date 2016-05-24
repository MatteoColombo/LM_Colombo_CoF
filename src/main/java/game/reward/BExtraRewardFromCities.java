package game.reward;

import game.player.Player;

public class BExtraRewardFromCities extends Bonus{
	
	// not really useful here, it may be removed in the future
	public static int VALUE = 60;
	
	public BExtraRewardFromCities(int amount) {
		super(amount);
	}

	@Override
	public int getValue() {
		return 0;
	}

	@Override
	public Bonus newCopy(int amount) {
		return new BExtraRewardFromCities(amount);
	}

	@Override
	public void assignBonusTo(Player p) {
		// TODO ask the player for cities
	}
	
}
