package model.market;

import java.util.ArrayList;
import java.util.List;

import model.exceptions.IllegalActionException;
import model.player.*;

public class Market {
	private List<Player> allPlayers;
	private List<GoodsBundle> allGoodsBundle;
	private final int ZERO = 0;

	public Market(List<Player> allPlayers) {
		this.allPlayers = allPlayers;
		this.allGoodsBundle = new ArrayList<>();
	}

	public List<Player> getAllPlayers() {
		return this.allPlayers;
	}

	public List<GoodsBundle> getAllGoodsBundle() {
		return this.allGoodsBundle;
	}

	public GoodsBundle getPlayerBundle(Player bundleOwner) throws IllegalActionException {
		for (GoodsBundle goodsBundle : this.allGoodsBundle)
			if (goodsBundle.getPlayerOwner().equals(bundleOwner))
				return goodsBundle;
		throw new IllegalActionException("this player has no bundle yet!");
	}

	public void newBundle(Player bundleOwner) throws IllegalActionException {
		for (GoodsBundle goodsBundle : this.allGoodsBundle)
			if (goodsBundle.getPlayerOwner().equals(bundleOwner))
				throw new IllegalActionException("this player has already a bundle!");
		this.allGoodsBundle.add(new GoodsBundle(bundleOwner));
	}

	public void addMerchandiseToBundle(Player bundleOwner, PermissionCard sellingPermissionCard, int askedPrice)
			throws IllegalActionException {
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
			throws IllegalActionException {
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

	public void addMerchandiseToBundle(Player bundleOwner, int amountOfSellingAssistants, int askedPriceForEachOne)
			throws IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (amountOfSellingAssistants <= bundleOwner.getAssistants().getAmount()) {
			bundleOwner.getAssistants().decreaseAmount(amountOfSellingAssistants);
			goodsBundle.setSellingAssistants(amountOfSellingAssistants, askedPriceForEachOne);
			return;
		}
		throw new IllegalActionException("this player doesn't have enough of this merchandise!");
	}

	public void giveBackUnsoldGoods(Player bundleOwner) throws IllegalActionException {
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

}