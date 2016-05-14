package game;

public abstract class Bonus {
	
	public static final Bonus[] allBonusType = { 
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
	
	public void increment(int given) {
		this.amount += given;
	}

	public abstract int getValue();
	public abstract Bonus deepCopy();
	
	// TODO assignBonusTo(Player) when player is ready
	
}
