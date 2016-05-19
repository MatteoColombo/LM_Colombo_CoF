package game.player;

public class Assistants {
	private int amount;

	public Assistants(int value) {
		this.amount = value;
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int value) {
		this.amount += this.amount;
	}

	public void decrease(int value) {
		this.amount -= value;
	}

}