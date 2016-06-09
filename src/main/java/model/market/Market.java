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

	public void addMerchandiseToBundle(Player bundleOwner, PermissionCard sellingPermissionCard, int askedPrice)
			throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		for (PermissionCard permissionCard : bundleOwner.getPermissionCard())
			if (permissionCard.equals(sellingPermissionCard)) {
				int index = bundleOwner.getPermissionCard().indexOf(permissionCard);
				if (!bundleOwner.getPermissionCard().get(index).getIfCardUsed()) {
					goodsBundle.getSellingPermissionCards()
							.add(new MPermissionCard(bundleOwner.getPermissionCard().remove(index), askedPrice));
					return;
				}
				throw new IllegalActionException("this player has already used this merchandise!");
			}
		throw new IllegalActionException("this player doesn't have this merchandise!");
	}

	public void addMerchandiseToBundle(Player bundleOwner, PoliticCard sellingPoliticCard, int askedPrice)
			throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		for (PoliticCard politicCard : bundleOwner.getPoliticCard())
			if (politicCard.equals(sellingPoliticCard)) {
				int index = bundleOwner.getPoliticCard().indexOf(politicCard);
				goodsBundle.getSellingPoliticCards()
						.add(new MPoliticCard(bundleOwner.getPoliticCard().remove(index), askedPrice));
				return;
			}
		throw new IllegalActionException("this player doesn't have this merchandise!");
	}

	public void addMerchandiseToBundle(Player bundleOwner, int amountOfSellingAssistants, int askedPriceForAll)
			throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (amountOfSellingAssistants <= bundleOwner.getAssistants().getAmount()) {
			bundleOwner.getAssistants().decreaseAmount(amountOfSellingAssistants);
			goodsBundle.setSellingAssistants(amountOfSellingAssistants, askedPriceForAll);
			return;
		}
		throw new IllegalActionException("this player doesn't have enough of this merchandise!");
	}

	public void giveBackUnsoldGoods(Player bundleOwner) throws IllegalActionException, NegativeException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (!goodsBundle.getSellingPermissionCards().isEmpty()) {
			for (MPermissionCard mPermissionCard : goodsBundle.getSellingPermissionCards()) {
				int index = goodsBundle.getSellingPermissionCards().indexOf(mPermissionCard);
				MPermissionCard returningPermissionCard = goodsBundle.getSellingPermissionCards().remove(index);
				bundleOwner.getPermissionCard().add(returningPermissionCard.getSellingPermissionCard());
			}
		}
		if (!goodsBundle.getSellingPoliticCards().isEmpty()) {
			for (MPoliticCard mPoliticCard : goodsBundle.getSellingPoliticCards()) {
				int index = goodsBundle.getSellingPoliticCards().indexOf(mPoliticCard);
				MPoliticCard returningPoliticCard = goodsBundle.getSellingPoliticCards().remove(index);
				bundleOwner.getPoliticCard().add(returningPoliticCard.getSellingPoliticCard());
			}
		}
		if (goodsBundle.getSellingAssistants().getSellingAssistants().getAmount() > ZERO) {
			bundleOwner.getAssistants()
					.increaseAmount(goodsBundle.getSellingAssistants().getSellingAssistants().getAmount());
			goodsBundle.setSellingAssistants(ZERO, ZERO);
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void buy(Player buyingPlayer) {
		this.shoppingCart = new GoodsBundle(buyingPlayer);

	}

	private void reciveBuyedGoods(Player buyingPlayer) throws IllegalActionException, NegativeException {
		if (!this.shoppingCart.getSellingPermissionCards().isEmpty()) {
			for (MPermissionCard mPermissionCard : this.shoppingCart.getSellingPermissionCards()) {
				int index = this.shoppingCart.getSellingPermissionCards().indexOf(mPermissionCard);
				MPermissionCard returningPermissionCard = this.shoppingCart.getSellingPermissionCards().remove(index);
				buyingPlayer.getPermissionCard().add(returningPermissionCard.getSellingPermissionCard());
			}
		}
		if (!this.shoppingCart.getSellingPoliticCards().isEmpty()) {
			for (MPoliticCard mPoliticCard : this.shoppingCart.getSellingPoliticCards()) {
				int index = this.shoppingCart.getSellingPoliticCards().indexOf(mPoliticCard);
				MPoliticCard returningPoliticCard = this.shoppingCart.getSellingPoliticCards().remove(index);
				buyingPlayer.getPoliticCard().add(returningPoliticCard.getSellingPoliticCard());
			}
		}
		if (this.shoppingCart.getSellingAssistants().getSellingAssistants().getAmount() > ZERO) {
			buyingPlayer.getAssistants()
					.increaseAmount(this.shoppingCart.getSellingAssistants().getSellingAssistants().getAmount());
			this.shoppingCart.setSellingAssistants(ZERO, ZERO);
		}
	}

}