package server.model.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.control.instruction.notify.NotifyMarketSellStarted;
import server.control.instruction.notify.NotifyPlayerDisconnected;
import server.control.instruction.update.NotifyMarketEnded;
import server.model.TurnManager;
import server.model.player.Assistants;
import server.model.player.Coins;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;

/**
 * A class that manages the Market of the Game.
 * <p>
 * When {@link #runMarket() is started}, the Market begins to
 * {@link #addItemOnSale(Soldable, int, Player) collect the selling requests} of
 * the Players until {@link #playerWantsToStop() they have all done} with that;
 * then {@link #buyItem(OnSaleItem, Player) each Player can buy} at most one of
 * the items that the others have offered, paying him with an amount of Coins
 * equal to the one asked for that item.
 * <p>
 * The Market phase always starts after all the Players have done a normal Game
 * turn.
 * 
 * @author Matteo Colombo
 * @see Coins
 * @see Game
 * @see OnSaleItem
 * @see Player
 * @see Soldable
 * @see TurnManager
 */
public class Market {
	private List<OnSaleItem> itemsOnSale;
	private List<Player> players;
	private boolean playerWantsToStop;
	private Logger logger = Logger.getGlobal();

	/**
	 * Initializes the list of the {@link OnSaleItem OnSaleItems} and receives
	 * the one of all the {@link Player Players}.
	 * 
	 * @param players
	 *            the Players of the Game
	 * @see Market
	 */
	public Market(List<Player> players) {
		this.players = players;
		itemsOnSale = new ArrayList<>();
	}

	/**
	 * Starts asking to each {@link Player} first what they would like to sell
	 * and then what they want to buy.
	 * 
	 * @see Market
	 */
	public void runMarket() {
		startSellingTurns();
		if (!itemsOnSale.isEmpty())
			startBuyingTurns();
		for (OnSaleItem item : itemsOnSale)
			giveToplayer(item, item.getOwner());
		itemsOnSale.clear();
	}

	/**
	 * Starts asking to each {@link Player} what they want to sell.
	 * 
	 * @see Market
	 */
	private void startSellingTurns() {
		for (Player p : players) {
			playerWantsToStop = false;
			if (p.getSuspended() || (p.getAssistants().getAmount() == 0 && p.getPermissionCard().isEmpty()
					&& p.getPoliticCard().isEmpty()))
				continue;
			try {
				p.getClient().notify(new NotifyMarketSellStarted());
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
				playerWantsToStop = true;
			}

			while (!playerWantsToStop) {
				try {
					p.getClient().askWichItemToSell();
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
					p.setSuspension(true);
					notifyDisconneted(p);
					playerWantsToStop = true;
				}
			}
		}
	}

	/**
	 * Starts asking to each {@link Player} what they want to buy.
	 * <p>
	 * It is permitted to buy at most one item per person per turn.
	 * 
	 * @see Market
	 */
	private void startBuyingTurns() {
		int starting = new Random().nextInt(players.size());
		for (int i = 0; i < players.size(); i++) {
			playerWantsToStop = false;
			if (players.get((i + starting) % players.size()).getSuspended())
				continue;
			while (!playerWantsToStop) {
				if (itemsOnSale.isEmpty()) {
					notifyPlayerEndBuyRound((i + starting) % players.size());
					return;
				}
				try {
					players.get((i + starting) % players.size()).getClient().askPlayerItemToBuy(itemsOnSale);
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
					players.get(i).setSuspension(true);
					notifyDisconneted(players.get(i));
					playerWantsToStop = true;
				}
			}

			notifyPlayerEndBuyRound((i + starting) % players.size());

		}
	}

	/**
	 * Notifies the end of the selling round to this {@link Player}.
	 * 
	 * @param index
	 *            the index of a Player
	 * @see Market
	 */
	private void notifyPlayerEndBuyRound(int index) {
		try {
			players.get((index) % players.size()).getClient().notify(new NotifyMarketEnded());
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
			players.get((index) % players.size()).setSuspension(true);
		}
	}

	/**
	 * Sets something on sale creating a new OnSaleItem.
	 * 
	 * @param item
	 *            the item that is put on sale
	 * @param price
	 *            the Coins amount
	 * @param owner
	 *            the Player owner
	 * @see Market
	 */
	public void addItemOnSale(Soldable item, int price, Player owner) {
		this.itemsOnSale.add(new OnSaleItem(item, owner, price));
	}

	/**
	 * Sets to <code>true</code> the attribute used to check if this
	 * {@link Player} wants to stop selling.
	 * 
	 * @see Market
	 */
	public void playerWantsToStop() {
		this.playerWantsToStop = true;
	}

	/**
	 * Realizes the sale paying the owner and scaling the {@link Coins} from the
	 * buyer; then it gives him the bought item(s).
	 * 
	 * @param item
	 *            the bought OnSaleItem
	 * @param p
	 *            the new owner of this OnSaleItem
	 * @see Market
	 */
	public void buyItem(OnSaleItem item, Player p) {
		itemsOnSale.remove(item);
		item.getOwner().getCoins().increaseAmount(item.getPrice());
		p.getCoins().decreaseAmount(item.getPrice());
		giveToplayer(item, p);
	}

	/**
	 * Gives the {@link OnSaleItem} to this {@link Player}.
	 * 
	 * @param item
	 *            the OnSaleItem
	 * @param p
	 *            the new owner of this OnSaleItem
	 * @see Market
	 */
	private void giveToplayer(OnSaleItem item, Player p) {
		Soldable soldItem = item.getItem();
		if (soldItem instanceof Assistants) {
			p.getAssistants().increaseAmount(((Assistants) soldItem).getAmount());
		} else if (soldItem instanceof PoliticCard)
			p.getPoliticCard().add((PoliticCard) soldItem);
		else if (soldItem instanceof PermissionCard)
			p.getPermissionCard().add((PermissionCard) soldItem);
	}

	/**
	 * Notifies the disconnection of a {@link Player} to all the others.
	 * 
	 * @param disconnected
	 *            the disconnected Player
	 * @see Market
	 */
	private void notifyDisconneted(Player disconnected) {
		for (Player p : players) {
			if (!p.getSuspended())
				try {
					p.getClient().notify(new NotifyPlayerDisconnected(disconnected.getName()));
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
		}
	}

}
