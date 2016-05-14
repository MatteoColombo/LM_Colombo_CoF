package game;

public abstract class Bonus {
	
	private static final Bonus[] allBonusType = { 
			 new BCoins(1),
			 new BAssistants(1),
			 new BNobilityPoints(1),
			 new BVictoryPoints(1),
			 new BPoliticCards(1),
			 new BExtraMainAction()};
	
	private int amount;
	
	public Bonus(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return this.amount;
	}

	public static Bonus[] getAllBonusType() {
		return allBonusType.clone();
	}
	
	public abstract int getValue();
	public abstract Bonus newCopy(int amount);
	
	// TODO assignBonusTo(Player) when player is ready
	
}
