package game;

public class VictoryPoints {
	private int amount;

	public VictoryPoints(int value) {
		this.amount = value;
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int value) {
		this.amount = this.amount + value;
	}
}