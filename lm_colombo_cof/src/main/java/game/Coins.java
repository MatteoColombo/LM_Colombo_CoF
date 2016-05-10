package game;

public class Coins {
	private int amount;

	public Coins() {
	}

	public int getAmount() {
		return this.amount;
	}

	public void increment(int valor) {
		this.amount = this.amount + valor;
	}

	private void decrease(int valor) {
		if (this.amount > valor)
			this.amount = this.amount - valor;
		else {
		}
	}

}