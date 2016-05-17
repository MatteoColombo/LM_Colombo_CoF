package game.player;

public class NoblePoints {
	private int amount;

	public NoblePoints(int value) {
		this.amount = value;
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int value) {
		this.amount = this.amount + value;
	}
}