package model.market;

import java.util.ArrayList;
import java.util.List;

import model.player.*;

public class GoodsBundle {
	private Player playerOwner;
	private List<MPermissionCard> sellingPermissionCards;
	private List<MPoliticCard> sellingPoliticCards;
	private MAssistants sellingAssistants;

	public GoodsBundle(Player playerOwner) {
		this.playerOwner = playerOwner;
		this.sellingPermissionCards = new ArrayList<>();
		this.sellingPoliticCards = new ArrayList<>();
		this.sellingAssistants = new MAssistants(0, 0);
	}

	public Player getPlayerOwner() {
		return this.playerOwner;
	}

	public List<MPermissionCard> getSellingPermissionCards() {
		return this.sellingPermissionCards;
	}

	public List<MPoliticCard> getSellingPoliticCards() {
		return this.sellingPoliticCards;
	}

	public void setSellingAssistants(int amountOfSellingAssistants, int askedPriceForEachOne) {
		int previousAssistans = this.sellingAssistants.getSellingAssistants().getAmount();
		int previousPrice = this.sellingAssistants.getPrice().getAmount();
		this.sellingAssistants.getSellingAssistants().decreaseAmount(previousAssistans);
		this.sellingAssistants.getPrice().decreaseAmount(previousPrice);
		this.sellingAssistants.getSellingAssistants().increaseAmount(amountOfSellingAssistants);
		this.sellingAssistants.getPrice().increaseAmount(askedPriceForEachOne);
	}

	public MAssistants getSellingAssistants() {
		return this.sellingAssistants;
	}

	public Coins getPermissionCardsPrice() {
		int i = 0;
		if (!this.sellingPermissionCards.isEmpty())
			for (MPermissionCard mPermissionCard : this.sellingPermissionCards)
				i += mPermissionCard.getPrice().getAmount();
		return new Coins(i);
	}

	public Coins getPoliticCardsPrice() {
		int i = 0;
		if (!this.sellingPoliticCards.isEmpty())
			for (MPoliticCard mPoliticCard : this.sellingPoliticCards)
				i += mPoliticCard.getPrice().getAmount();
		return new Coins(i);
	}

	public Coins getAssistantsPrice() {
		return new Coins(this.sellingAssistants.getSellingAssistants().getAmount()
				* this.sellingAssistants.getPrice().getAmount());
	}

	public Coins getBundlePrice() {
		int i = getPoliticCardsPrice().getAmount() + getPermissionCardsPrice().getAmount()
				+ getAssistantsPrice().getAmount();
		return new Coins(i);
	}

}