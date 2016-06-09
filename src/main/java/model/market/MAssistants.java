package model.market;

import model.player.*;

public class MAssistants extends Merchandise {
	private Assistants sellingAssistants;

	public MAssistants(int amountOfSellingAssistants, int askedPriceForAll) {
		super(askedPriceForAll);
		this.sellingAssistants = new Assistants(amountOfSellingAssistants);
	}

	public Assistants getSellingAssistants() {
		return this.sellingAssistants;
	}

}