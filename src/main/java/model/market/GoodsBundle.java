package model.market;

import java.util.ArrayList;
import java.util.List;

import model.player.*;

public class GoodsBundle {
	private Player playerOwner;
	private List<PermissionCard> sellingPermissionCards;
	private List<PoliticCard> sellingPoliticCards;
	private Assistants sellingAssistants;
	private Coins permissionCardsPrice;
	private Coins politicCardsPrice;
	private Coins assistantsPrice;
	private final int DEFAULT = 0;

	public GoodsBundle(Player playerOwner) {
		this.playerOwner = playerOwner;
		this.sellingPermissionCards = new ArrayList<>();
		this.sellingPoliticCards = new ArrayList<>();
		this.sellingAssistants = new Assistants(DEFAULT);
		this.permissionCardsPrice = new Coins(DEFAULT);
		this.politicCardsPrice = new Coins(DEFAULT);
		this.assistantsPrice = new Coins(DEFAULT);
	}

	public Player getPlayerOwner() {
		return this.playerOwner;
	}

	public List<PermissionCard> getSellingPermissionCards() {
		return this.sellingPermissionCards;
	}

	public List<PoliticCard> getSellingPoliticCards() {
		return this.sellingPoliticCards;
	}

	public Assistants getSellingAssistants() {
		return this.sellingAssistants;
	}

	public void setSellingAssistants(int amountOfSellingAssistants) {
		int previousAssistans = this.sellingAssistants.getAmount();
		this.sellingAssistants.decreaseAmount(previousAssistans);
		this.sellingAssistants.increaseAmount(amountOfSellingAssistants);
	}

	public Coins getPermissionCardsPrice() {
		return this.permissionCardsPrice;
	}

	public void setPermissionCardsPrice(int askedPermissionCardsPrice) {
		int previousPrice = this.permissionCardsPrice.getAmount();
		this.permissionCardsPrice.decreaseAmount(previousPrice);
		this.permissionCardsPrice.increaseAmount(askedPermissionCardsPrice);
	}

	public Coins getPoliticCardsPrice() {
		return this.politicCardsPrice;
	}

	public void setPoliticCardsPrice(int askedPoliticCardsPrice) {
		int previousPrice = this.politicCardsPrice.getAmount();
		this.politicCardsPrice.decreaseAmount(previousPrice);
		this.politicCardsPrice.increaseAmount(askedPoliticCardsPrice);
	}

	public Coins getAssistantsPrice() {
		return this.assistantsPrice;
	}

	public void setAssistantsPrice(int askedAssistantsPrice) {
		int previousPrice = this.assistantsPrice.getAmount();
		this.assistantsPrice.decreaseAmount(previousPrice);
		this.assistantsPrice.increaseAmount(askedAssistantsPrice);
	}

	public Coins getBundleTotalPrice() {
		return new Coins(getPermissionCardsPrice().getAmount() + getPoliticCardsPrice().getAmount()
				+ getAssistantsPrice().getAmount());
	}

}