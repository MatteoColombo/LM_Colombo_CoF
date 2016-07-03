package server.model.market;

import java.io.Serializable;

import server.model.configuration.Configuration;
import server.model.player.Player;
/**
 * The class which contains the items sold in the market
 * Each item is characterized by a Soldable object, an owner and a price
 * @author Matteo Colombo
 *
 */
public class OnSaleItem implements Serializable{

	private static final long serialVersionUID = 5078039274488399580L;
	private Soldable item;
	private Player owner;
	private int price;
	
	/**
	 * Creates the OnSaleItem
	 * @param item the real item which is put on sale. Every item must implement Soldable
	 * @param owner the owner, a Player
	 * @param price the price, an integer
	 */
	public OnSaleItem(Soldable item, Player owner, int price){
		this.owner= owner;
		this.item=item;
		this.price=price;
	}
	
	/**
	 * The price of the item on sale
	 * @return an integer
	 */
	public int getPrice(){
		return this.price;
	}
	
	/**
	 * Returns the owner of the on sale item
	 * @return a Player
	 */
	public Player getOwner(){
		return this.owner;
	}
	
	/**
	 * Returns the actual item which is on sale
	 * @return an Item of Soldable type
	 */
	public Soldable getItem(){
		return item;
	}
	
	/**
	 * Returns a string which is is used to print the item, if needed
	 * @param config the configuration, it is needed to translate the colors 
	 * @return a string
	 */
	public String printedMessage(Configuration config){
		return item.getMarketMessage(config)+" |  price: "+price+" | owner: "+owner.getName();
	}
}
