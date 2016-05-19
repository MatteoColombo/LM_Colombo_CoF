package game.player;

import game.board.exceptions.NegativeException;

public class Coins {
	private int amount;

	public Coins(int value) {
		this.amount = value;
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int value) {
		this.amount += value;
	}

	public void decrease(int value) throws NegativeException {
		this.amount -= value;
	}

}