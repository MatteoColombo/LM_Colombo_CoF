package game.player;

import game.board.exceptions.NegativeException;

public class Assistants {
	private int amount;

	public Assistants(int value) {
		this.amount = value;
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int value) {
		this.amount = this.amount + value;
	}

	public void decrease(int value) throws NegativeException {
		if (this.amount > value)
			this.amount = this.amount - value;
		else
			throw new NegativeException();
	}

}