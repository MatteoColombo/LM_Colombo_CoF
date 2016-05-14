package game;

public abstract class Bonus {
	
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
	public abstract boolean isInstantiableFor(RewardType rt);
	
	// TODO assignBonusTo(Player) when player is ready
	
}
