package model.market;

import model.player.*;

public class MPermissionCard extends Merchandise {
	private PermissionCard sellingPermissionCard;

	public MPermissionCard(PermissionCard sellingPermissionCard, int askedPrice) {
		super(askedPrice);
		this.sellingPermissionCard = sellingPermissionCard;
	}

	public PermissionCard getSellingPermissionCard() {
		return this.sellingPermissionCard;
	}

}