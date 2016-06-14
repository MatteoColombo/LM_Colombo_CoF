package model.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.exceptions.*;
import model.player.*;

public class Market {
	private List<Player> allPlayers;
	private List<GoodsBundle> allGoodsBundle;
	private Player playerBuyer;
	private Boolean playerWantToStopBuying;
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

	public GoodsBundle getPlayerBundle(Player bundleOwner) {
		for (GoodsBundle goodsBundle : this.allGoodsBundle)
			if (goodsBundle.getPlayerOwner().equals(bundleOwner))
				return goodsBundle;
		return null;
	}

	// Bundles creations
	public void assignPermissionCardsToBundle(Player bundleOwner, List<PermissionCard> sellingPermissionCards,
			int askedPermissionCardsPrice) throws IllegalActionException {
		for (PermissionCard sellingPermissionCard : sellingPermissionCards)
			addOnePermissionCardToBundle(bundleOwner, sellingPermissionCard);
		setPermissionCardsBundlePrice(bundleOwner, askedPermissionCardsPrice);
	}

	public void addOnePermissionCardToBundle(Player bundleOwner, PermissionCard sellingPermissionCard)
			throws IllegalActionException {
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

	public void setPermissionCardsBundlePrice(Player bundleOwner, int askedPermissionCardsPrice) {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		goodsBundle.setPermissionCardsPrice(askedPermissionCardsPrice);
	}

	public void removePermissionCardsFromBundle(Player bundleOwner) {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		Iterator<PermissionCard> permissionCardsIterator = goodsBundle.getSellingPermissionCards().iterator();
		if (!goodsBundle.getSellingPermissionCards().isEmpty())
			while (permissionCardsIterator.hasNext()) {
				bundleOwner.getPermissionCard().add(permissionCardsIterator.next());
				permissionCardsIterator.remove();
			}
		setPermissionCardsBundlePrice(bundleOwner, ZERO);
	}

	public void removeOnePermissionCardFromBundle(Player bundleOwner, PermissionCard removingPermissionCard)
			throws IllegalActionException {
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
			int askedPoliticCardsPrice) throws IllegalActionException {
		for (PoliticCard sellingPoliticCard : sellingPoliticCards)
			addOnePoliticCardToBundle(bundleOwner, sellingPoliticCard);
		setPoliticCardsBundlePrice(bundleOwner, askedPoliticCardsPrice);
	}

	public void addOnePoliticCardToBundle(Player bundleOwner, PoliticCard sellingPoliticCard)
			throws IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		for (PoliticCard politicCard : bundleOwner.getPoliticCard())
			if (politicCard.equals(sellingPoliticCard)) {
				int index = bundleOwner.getPoliticCard().indexOf(politicCard);
				goodsBundle.getSellingPoliticCards().add(bundleOwner.getPoliticCard().remove(index));
				return;
			}
		throw new IllegalActionException("this player doesn't have this merchandise!");
	}

	public void setPoliticCardsBundlePrice(Player bundleOwner, int askedPoliticCardsPrice) {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		goodsBundle.setPoliticCardsPrice(askedPoliticCardsPrice);
	}

	public void removePoliticCardsFromBundle(Player bundleOwner) {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		Iterator<PoliticCard> politicCardsIterator = goodsBundle.getSellingPoliticCards().iterator();
		if (!goodsBundle.getSellingPoliticCards().isEmpty())
			while (politicCardsIterator.hasNext()) {
				bundleOwner.getPoliticCard().add(politicCardsIterator.next());
				politicCardsIterator.remove();
			}
		setPoliticCardsBundlePrice(bundleOwner, ZERO);
	}

	public void removeOnePoliticCardFromBundle(Player bundleOwner, PoliticCard removingPoliticCard)
			throws IllegalActionException {
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
			throws IllegalActionException {
		changeBundleAssistants(bundleOwner, amountOfSellingAssistants);
		setAssistantsBundlePrice(bundleOwner, askedAssistantsPrice);
	}

	public void changeBundleAssistants(Player bundleOwner, int amountOfSellingAssistants)
			throws IllegalActionException {
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

	public void setAssistantsBundlePrice(Player bundleOwner, int askedAssistantsPrice) {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		goodsBundle.setAssistantsPrice(askedAssistantsPrice);
	}

	public void removeAssistantsFromBundle(Player bundleOwner) {
		GoodsBundle goodsBundle = getPlayerBundle(bundleOwner);
		if (goodsBundle.getSellingAssistants().getAmount() > ZERO) {
			bundleOwner.getAssistants().increaseAmount(goodsBundle.getSellingAssistants().getAmount());
			goodsBundle.setSellingAssistants(ZERO);
		}
		setAssistantsBundlePrice(bundleOwner, ZERO);
	}

	// Buy merchandises
	public void startBuying(Player playerBuyer) throws IllegalActionException {
		if (this.playerBuyer.equals(null)) {
			this.playerBuyer = playerBuyer;
			this.playerWantToStopBuying = false;
			while (!this.playerWantToStopBuying && this.playerBuyer.getCoins().getAmount() > ZERO) {
				try {
					this.playerBuyer.getClient().askPlayerWhichMerchandiseBuy(this.playerBuyer, this.allPlayers);
				} catch (IOException exc) {
					System.out.println(exc);
					break;
				}
			}
			this.playerBuyer = null;
			return;
		}
		throw new IllegalActionException("only one player can buy at the time!");
	}

	public void stopBuying(Player playerBuyer) throws IllegalActionException {
		if (this.playerBuyer.equals(playerBuyer)) {
			boolean decision = false;
			try {
				decision = this.playerBuyer.getClient().askPlayerConfirmation();
			} catch (IOException exc) {
				System.out.println(exc);
			}
			if (decision)
				this.playerWantToStopBuying = true;
			return;
		}
		throw new IllegalActionException("this player is not buying now!");
	}

	public void buyPermissionCardsBundle(Player playerSeller) throws IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(playerSeller);
		if (this.playerBuyer.getCoins().getAmount() >= goodsBundle.getPermissionCardsPrice().getAmount()
				&& !goodsBundle.getSellingPermissionCards().isEmpty()
				&& goodsBundle.getPermissionCardsPrice().getAmount() > ZERO) {
			boolean decision = false;
			try {
				decision = this.playerBuyer.getClient().askPlayerConfirmation();
			} catch (IOException exc) {
				System.out.println(exc);
			}
			if (decision) {
				for (int index = 0; index < goodsBundle.getSellingPermissionCards().size(); index++)
					this.playerBuyer.getPermissionCard().add(goodsBundle.getSellingPermissionCards().remove(index));
				this.playerBuyer.getCoins().decreaseAmount(goodsBundle.getPermissionCardsPrice().getAmount());
				playerSeller.getCoins().increaseAmount(goodsBundle.getPermissionCardsPrice().getAmount());
				setPermissionCardsBundlePrice(playerSeller, ZERO);
			}
			return;
		}
		throw new IllegalActionException("this player doesn't have enough coins to buy this!");
	}

	public void buyPoliticCardsBundle(Player playerSeller) throws IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(playerSeller);
		if (this.playerBuyer.getCoins().getAmount() >= goodsBundle.getPoliticCardsPrice().getAmount()
				&& !goodsBundle.getSellingPoliticCards().isEmpty()
				&& goodsBundle.getPoliticCardsPrice().getAmount() > ZERO) {
			boolean decision = false;
			try {
				decision = this.playerBuyer.getClient().askPlayerConfirmation();
			} catch (IOException exc) {
				System.out.println(exc);
			}
			if (decision) {
				for (int index = 0; index < goodsBundle.getSellingPoliticCards().size(); index++)
					this.playerBuyer.getPoliticCard().add(goodsBundle.getSellingPoliticCards().remove(index));
				this.playerBuyer.getCoins().decreaseAmount(goodsBundle.getPoliticCardsPrice().getAmount());
				playerSeller.getCoins().increaseAmount(goodsBundle.getPoliticCardsPrice().getAmount());
				setPoliticCardsBundlePrice(playerSeller, ZERO);
			}
			return;
		}
		throw new IllegalActionException("this player doesn't have enough coins to buy this!");
	}

	public void buyAssistantsBundle(Player playerSeller) throws IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(playerSeller);
		if (this.playerBuyer.getCoins().getAmount() >= goodsBundle.getAssistantsPrice().getAmount()
				&& goodsBundle.getSellingAssistants().getAmount() > ZERO
				&& goodsBundle.getAssistantsPrice().getAmount() > ZERO) {
			boolean decision = false;
			try {
				decision = this.playerBuyer.getClient().askPlayerConfirmation();
			} catch (IOException exc) {
				System.out.println(exc);
			}
			if (decision) {
				this.playerBuyer.getAssistants().increaseAmount(goodsBundle.getSellingAssistants().getAmount());
				this.playerBuyer.getCoins().decreaseAmount(goodsBundle.getAssistantsPrice().getAmount());
				playerSeller.getCoins().increaseAmount(goodsBundle.getAssistantsPrice().getAmount());
				assignAssistantsToBundle(playerSeller, ZERO, ZERO);
			}
			return;
		}
		throw new IllegalActionException("this player doesn't have enough coins to buy this!");
	}

	public void buyFullBundle(Player playerSeller) throws IllegalActionException {
		GoodsBundle goodsBundle = getPlayerBundle(playerSeller);
		if (this.playerBuyer.getCoins().getAmount() >= goodsBundle.getBundleTotalPrice().getAmount()
				&& goodsBundle.getBundleTotalPrice().getAmount() > ZERO) {
			boolean decision = false;
			try {
				decision = this.playerBuyer.getClient().askPlayerConfirmation();
			} catch (IOException exc) {
				System.out.println(exc);
			}
			if (decision) {
				for (int index = 0; index < goodsBundle.getSellingPermissionCards().size(); index++)
					this.playerBuyer.getPermissionCard().add(goodsBundle.getSellingPermissionCards().remove(index));
				this.playerBuyer.getCoins().decreaseAmount(goodsBundle.getPermissionCardsPrice().getAmount());
				playerSeller.getCoins().increaseAmount(goodsBundle.getPermissionCardsPrice().getAmount());
				setPermissionCardsBundlePrice(playerSeller, ZERO);
				for (int index = 0; index < goodsBundle.getSellingPoliticCards().size(); index++)
					this.playerBuyer.getPoliticCard().add(goodsBundle.getSellingPoliticCards().remove(index));
				this.playerBuyer.getCoins().decreaseAmount(goodsBundle.getPoliticCardsPrice().getAmount());
				playerSeller.getCoins().increaseAmount(goodsBundle.getPoliticCardsPrice().getAmount());
				setPoliticCardsBundlePrice(playerSeller, ZERO);
				this.playerBuyer.getAssistants().increaseAmount(goodsBundle.getSellingAssistants().getAmount());
				this.playerBuyer.getCoins().decreaseAmount(goodsBundle.getAssistantsPrice().getAmount());
				playerSeller.getCoins().increaseAmount(goodsBundle.getAssistantsPrice().getAmount());
				assignAssistantsToBundle(playerSeller, ZERO, ZERO);
			}
			return;
		}
		throw new IllegalActionException("this player doesn't have enough coins to buy this!");
	}

	// Market end (for this turn)
	public void giveBackUnsoldGoods() {
		for (Player player : this.allPlayers) {
			removePermissionCardsFromBundle(player);
			removePoliticCardsFromBundle(player);
			removeAssistantsFromBundle(player);
		}
	}

}
