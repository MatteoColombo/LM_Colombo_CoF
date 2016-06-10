package model.market;

import java.util.ArrayList;
import java.util.List;

import model.exceptions.*;
import model.player.*;

public class Market {
	private List<Player> allPlayers;
	private List<GoodsBundle> allGoodsBundle;
	private GoodsBundle shoppingCart;
	private final int ZERO = 0;

	public Market(List<Player> allPlayers) {
		this.allPlayers = allPlayers;
		this.allGoodsBundle = new ArrayList<>();
		for (Player player : this.allPlayers)
			this.allGoodsBundle.add(new GoodsBundle(player));
	}

	public List<Player> getAllPlayers() {
		return this.allPlayers;
	}

	public List<GoodsBundle> getAllGoodsBundle() {
		return this.allGoodsBundle;
	}

	public GoodsBundle getPlayerBundle(Player bundleOwner) throws NegativeException {
		for (GoodsBundle goodsBundle : this.allGoodsBundle)
			if (goodsBundle.getPlayerOwner().equals(bundleOwner))
				return goodsBundle;
		throw new NegativeException("something went wrong!");
	}

	public void assignPermissionCardsToBundle(Player bundleOwner, List<PermissionCard> sellingPermissionCards,
			int askedPermissionCardsPrice) throws IllegalActionException, NegativeException {
		for (PermissionCard sellingPermissionCard : sellingPermissionCards)
			addOnePermissionCardToBundle(bundleOwner, sellingPermissionCard);
		setPermissionCardsBundlePrice(bundleOwner, askedPermissionCardsPrice);
	}

	public void addOnePermissionCardToBundle(Player bundleOwner, PermissionCard sellingPermissionCard)
			throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		for (PermissionCard permissionCard : bundleOwner.getPermissionCard())
			if (permissionCard.equals(sellingPermissionCard)) {
				int index = bundleOwner.getPermissionCard().indexOf(permissionCard);
				if (!bundleOwner.getPermissionCard().get(index).getIfCardUsed()) {
					goodsBundle.getSellingPermissionCards().add(bundleOwner.getPermissionCard().remove(index));
					return;
				}
				throw new IllegalActionException("this player has already used this merchandise!");
			}
		throw new IllegalActionException("this player doesn't have this merchandise!");
	}

	public void setPermissionCardsBundlePrice(Player bundleOwner, int askedPermissionCardsPrice)
			throws NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		goodsBundle.setPermissionCardsPrice(askedPermissionCardsPrice);
	}

	public void removePermissionCardsFromBundle(Player bundleOwner) throws NegativeException, IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (!goodsBundle.getSellingPermissionCards().isEmpty())
			for (PermissionCard removingPermissionCard : goodsBundle.getSellingPermissionCards())
				removeOnePermissionCardFromBundle(bundleOwner, removingPermissionCard);
		setPermissionCardsBundlePrice(bundleOwner, ZERO);
	}

	public void removeOnePermissionCardFromBundle(Player bundleOwner, PermissionCard removingPermissionCard)
			throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (!goodsBundle.getSellingPermissionCards().isEmpty()) {
			for (PermissionCard permissionCard : goodsBundle.getSellingPermissionCards())
				if (permissionCard.equals(removingPermissionCard)) {
					int index = goodsBundle.getSellingPermissionCards().indexOf(permissionCard);
					bundleOwner.getPermissionCard().add(goodsBundle.getSellingPermissionCards().remove(index));
					return;
				}
			throw new IllegalActionException("this merchandise is not present in this player's bundle!");
		}
		throw new IllegalActionException("this merchandise is not present in this player's bundle!");
	}

	public void assignPoliticCardsToBundle(Player bundleOwner, List<PoliticCard> sellingPoliticCards,
			int askedPoliticCardsPrice) throws IllegalActionException, NegativeException {
		for (PoliticCard sellingPoliticCard : sellingPoliticCards)
			addOnePoliticCardToBundle(bundleOwner, sellingPoliticCard);
		setPoliticCardsBundlePrice(bundleOwner, askedPoliticCardsPrice);
	}

	public void addOnePoliticCardToBundle(Player bundleOwner, PoliticCard sellingPoliticCard)
			throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		for (PoliticCard politicCard : bundleOwner.getPoliticCard())
			if (politicCard.equals(sellingPoliticCard)) {
				int index = bundleOwner.getPoliticCard().indexOf(politicCard);
				goodsBundle.getSellingPoliticCards().add(bundleOwner.getPoliticCard().remove(index));
				return;
			}
		throw new IllegalActionException("this player doesn't have this merchandise!");
	}

	public void setPoliticCardsBundlePrice(Player bundleOwner, int askedPoliticCardsPrice) throws NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		goodsBundle.setPoliticCardsPrice(askedPoliticCardsPrice);
	}

	public void removePoliticCardsFromBundle(Player bundleOwner) throws NegativeException, IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (!goodsBundle.getSellingPoliticCards().isEmpty())
			for (PoliticCard removingPoliticCard : goodsBundle.getSellingPoliticCards())
				removeOnePoliticCardFromBundle(bundleOwner, removingPoliticCard);
		setPoliticCardsBundlePrice(bundleOwner, ZERO);
	}

	public void removeOnePoliticCardFromBundle(Player bundleOwner, PoliticCard removingPoliticCard)
			throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (!goodsBundle.getSellingPoliticCards().isEmpty()) {
			for (PoliticCard politicCard : goodsBundle.getSellingPoliticCards())
				if (politicCard.equals(removingPoliticCard)) {
					int index = goodsBundle.getSellingPoliticCards().indexOf(politicCard);
					bundleOwner.getPoliticCard().add(goodsBundle.getSellingPoliticCards().remove(index));
					return;
				}
			throw new IllegalActionException("this merchandise is not present in this player's bundle!");
		}
		throw new IllegalActionException("this merchandise is not present in this player's bundle!");
	}

	public void assignAssistantsToBundle(Player bundleOwner, int amountOfSellingAssistants, int askedAssistantsPrice)
			throws IllegalActionException, NegativeException {
		changeBundleAssistants(bundleOwner, amountOfSellingAssistants);
		setAssistantsBundlePrice(bundleOwner, askedAssistantsPrice);
	}

	public void changeBundleAssistants(Player bundleOwner, int amountOfSellingAssistants)
			throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		int realAssistants = bundleOwner.getAssistants().getAmount() + goodsBundle.getSellingAssistants().getAmount();
		if (realAssistants >= amountOfSellingAssistants) {
			bundleOwner.getAssistants().increaseAmount(goodsBundle.getSellingAssistants().getAmount());
			bundleOwner.getAssistants().decreaseAmount(amountOfSellingAssistants);
			goodsBundle.setSellingAssistants(amountOfSellingAssistants);
			return;
		}
		throw new IllegalActionException("this player doesn't have enough of this merchandise!");
	}

	public void setAssistantsBundlePrice(Player bundleOwner, int askedAssistantsPrice) throws NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		goodsBundle.setAssistantsPrice(askedAssistantsPrice);
	}

	public void removeAssistantsFromBundle(Player bundleOwner) throws NegativeException, IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (goodsBundle.getSellingAssistants().getAmount() > ZERO)
			changeBundleAssistants(bundleOwner, ZERO);
		setAssistantsBundlePrice(bundleOwner, ZERO);
	}

	public void giveBackUnsoldGoods(Player bundleOwner) throws IllegalActionException, NegativeException {
		removePermissionCardsFromBundle(bundleOwner);
		removePoliticCardsFromBundle(bundleOwner);
		removeAssistantsFromBundle(bundleOwner);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void buy(Player buyingPlayer) {
		this.shoppingCart = new GoodsBundle(buyingPlayer);

	}

	private void reciveBoughtGoods(Player buyingPlayer) throws IllegalActionException, NegativeException {
		if (!this.shoppingCart.getSellingPermissionCards().isEmpty()) {
			for (PermissionCard permissionCard : this.shoppingCart.getSellingPermissionCards()) {
				int index = this.shoppingCart.getSellingPermissionCards().indexOf(permissionCard);
				buyingPlayer.getPermissionCard().add(this.shoppingCart.getSellingPermissionCards().remove(index));
			}
		}
		if (!this.shoppingCart.getSellingPoliticCards().isEmpty()) {
			for (PoliticCard politicCard : this.shoppingCart.getSellingPoliticCards()) {
				int index = this.shoppingCart.getSellingPoliticCards().indexOf(politicCard);
				buyingPlayer.getPoliticCard().add(this.shoppingCart.getSellingPoliticCards().remove(index));
			}
		}
		if (this.shoppingCart.getSellingAssistants().getAmount() > ZERO) {
			buyingPlayer.getAssistants().increaseAmount(this.shoppingCart.getSellingAssistants().getAmount());
			this.shoppingCart.setSellingAssistants(ZERO);
			this.shoppingCart.setSellingAssistants(ZERO);
		}
	}

}