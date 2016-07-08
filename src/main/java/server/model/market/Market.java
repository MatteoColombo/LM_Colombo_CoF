package server.model.market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import server.control.dialogue.notify.NotifyMarketSellStarted;
import server.model.player.Assistants;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;

/**
 * This is the class which manages the market
 * 
 * @author Matteo Colombo
 *
 */
public class Market {
	private List<OnSaleItem> itemsOnSale;
	private List<Player> players;
	private boolean playerWantsToStop;
	private Logger logger = Logger.getGlobal();

	/**
	 * Instantiates the ArrayList of the items on sale and saves the players
	 * 
	 * @param players the players of the game
	 */
	public Market(List<Player> players) {
		this.players = players;
		itemsOnSale = new ArrayList<>();
	}

	/**
	 * This is the class which manages the market rounds Asks to each player
	 * what they whish to sell and then what they want to buy
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
	 * Ask the players what the want to sell
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
					playerWantsToStop = true;
				}
			}
		}
	}

	/**
	 * Start ask each player what they want to buy It is permitted to buy at
	 * most one item per person per turn
	 */
	private void startBuyingTurns() {
		int starting = new Random().nextInt(players.size());
		for (int i = 0; i < players.size(); i++) {
			playerWantsToStop = false;
			if (players.get((i + starting) % players.size()).getSuspended())
				continue;
			while (!playerWantsToStop) {
				if (itemsOnSale.isEmpty())
					return;
				try {
					players.get((i + starting) % players.size()).getClient().askPlayerItemToBuy(itemsOnSale);
				} catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
					playerWantsToStop = true;
				}
			}
		}
	}

	/**
	 * Sets something on sale
	 * 
	 * @param item
	 *            the item set on sale
	 */
	public void addItemOnSale(Soldable item, int price, Player owner) {
		this.itemsOnSale.add(new OnSaleItem(item, owner, price));
	}

	/**
	 * Sets to true the attribute used to check if the player wants to stop
	 * selling
	 */
	public void playerWantsToStop() {
		this.playerWantsToStop = true;
	}

	/**
	 * Realizes the sale. It pays the owner and scales the money from the buyer,
	 * then it gives him the bought item(s)
	 * 
	 * @param item
	 *            the item which was bought
	 * @param p
	 *            the player who buys
	 */
	public void buyItem(OnSaleItem item, Player p) {
		itemsOnSale.remove(item);
		item.getOwner().getCoins().increaseAmount(item.getPrice());
		p.getCoins().decreaseAmount(item.getPrice());
		giveToplayer(item, p);
	}

	/**
	 * Give the player an OnSaleItem, it is used
	 * 
	 * @param item
	 * @param p
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

}
