package model.market;

import model.player.*;

public class MPoliticCard extends Merchandise {
	private PoliticCard sellingPoliticCard;

	public MPoliticCard(PoliticCard sellingPoliticCard, int askedPrice) {
		super(askedPrice);
		this.sellingPoliticCard = sellingPoliticCard;
	}

	public PoliticCard getSellingPoliticCard() {
		return this.sellingPoliticCard;
	}

}