package model.market;

import model.player.*;

public abstract class Merchandise {
	private Coins price;

	public Merchandise(int askedPrice) {
		this.price = new Coins(askedPrice);
	}

	public Coins getPrice() {
		return this.price;
	}

}