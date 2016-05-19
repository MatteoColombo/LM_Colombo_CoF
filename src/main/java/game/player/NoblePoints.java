package game.player;

public class NoblePoints {
	private int amount;

	public NoblePoints(int initialValue) {
		this.amount = initialValue;
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int value) {
		this.amount += value;
	}
}