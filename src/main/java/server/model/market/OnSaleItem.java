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
	
	public String printedMessage(Configuration config){
		return item.getMarketMessage(config)+" |  price: "+price+" | owner: "+owner.getName();
	}
}
