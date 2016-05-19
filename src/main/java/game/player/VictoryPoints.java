package game.player;

public class VictoryPoints {
	private int amount;

	public VictoryPoints(int initialValue) {
		this.amount = initialValue;
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int value) {
		this.amount += value;
	}
}