package game.reward;

public abstract class Bonus {
	
	private static final Bonus[] allStandardBonus = { 
			 new BCoins(1),
			 new BAssistants(1),
			 new BNobilityPoints(1),
			 new BVictoryPoints(1),
			 new BPoliticCards(1),
			 new BExtraMainAction()}; // 1 by default
	
	private int amount;
	
	public Bonus(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return this.amount;
	}

	public static Bonus[] getAllStandardBonus() {
		return allStandardBonus.clone();
	}
	
	public abstract int getValue();
	public abstract Bonus newCopy(int amount);
	
	// TODO assignBonusTo(Player) when player is ready
	
}
