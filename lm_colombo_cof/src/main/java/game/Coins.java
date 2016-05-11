package game;

public class Coins {
	private int amount;

	public Coins(int value) {
		this.amount = value;
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int value) {
		this.amount = this.amount + value;
	}

	public void decrease(int value) {
		if (this.amount > value)
			this.amount = this.amount - value;
		else {
		}
	}

}