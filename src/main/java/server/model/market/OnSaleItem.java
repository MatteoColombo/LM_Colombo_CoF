package server.model.market;

import java.io.Serializable;

import server.model.configuration.Configuration;
import server.model.player.Coins;
import server.model.player.Player;

/**
 * A class that contains all the items sold in the Market.
 * <p>
 * Each object is characterized by {@link #getItem() a Soldable item},
 * {@link #getOwner() a Player owner} and {@link #getPrice() a Coins price} .
 * it's also possible to print {@link #printedMessage(Configuration) a string
 * with information} about the item and {@link #newCopy() create a new copy} of
 * this object.
 * <p>
 * Each item have to implement the Soldable interface.
 * 
 * @author Matteo Colombo
 * @see Coins
 * @see Market
 * @see Player
 * @see Soldable
 */
public class OnSaleItem implements Serializable {
	private static final long serialVersionUID = 5078039274488399580L;
	private Soldable item;
	private Player owner;
	private int price;

	/**
	 * Creates a new OnSaleItem.
	 * 
	 * @param item
	 *            the real item that is put on sale in the Market
	 * @param owner
	 *            the Player owner
	 * @param price
	 *            the Coins amount
	 * @see OnSaleItem
	 */
	public OnSaleItem(Soldable item, Player owner, int price) {
		this.owner = owner;
		this.item = item;
		this.price = price;
	}

	/**
	 * Clones an existing OnSaleItem.
	 * 
	 * @param item
	 *            the OnSaleItem that will be cloned
	 * @see OnSaleItem
	 */
	private OnSaleItem(OnSaleItem item) {
		this.item = item.item.newCopy();
		this.owner = item.owner.getClientCopy();
		this.price = item.price;
	}

	/**
	 * Returns the price of the item on sale.
	 * 
	 * @return the price of the item
	 * @see OnSaleItem
	 */
	public int getPrice() {
		return this.price;
	}

	/**
	 * Returns the owner of the item on sale.
	 * 
	 * @return the owner of the item
	 * @see OnSaleItem
	 */
	public Player getOwner() {
		return this.owner;
	}

	/**
	 * Returns the real item on sale.
	 * 
	 * @return the real item on sale
	 * @see OnSaleItem
	 */
	public Soldable getItem() {
		return item;
	}

	/**
	 * Prints a string with information about this object.
	 * 
	 * @param config
	 *            the Configuration; it is needed to translate the Colors into
	 *            text
	 * @return an information string of this object
	 * @see OnSaleItem
	 */
	public String printedMessage(Configuration config) {
		return item.getMarketMessage(config) + " |  price: " + price + " | owner: " + owner.getName();
	}

	/**
	 * Return a new copy of this object.
	 * 
	 * @return a new copy of this object
	 * @see OnSaleItem
	 */
	public OnSaleItem newCopy() {
		return new OnSaleItem(this);
	}
}
