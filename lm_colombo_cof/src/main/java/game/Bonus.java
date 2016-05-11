package game;

public abstract class Bonus {
	
	protected int amount;
	protected boolean mustBeAlone;
	protected int value;
	
	public Bonus(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public void increment(int given) {
		this.amount += given;
	}
	
	public boolean mustBeAlone() {
		return this.mustBeAlone;
	}
	
	public int getValue() {
		return this.value;
	}

	public abstract Bonus deepCopy();
	public abstract boolean isInstantiableFor(RewardType rt);
	
}
