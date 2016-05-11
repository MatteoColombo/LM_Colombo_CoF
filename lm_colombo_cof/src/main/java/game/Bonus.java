package game;

public abstract class Bonus {
	
	protected int amount;

	
	public Bonus(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public abstract boolean isInstantiableFor(RewardType rt);
	public abstract int getValue();
	
}
