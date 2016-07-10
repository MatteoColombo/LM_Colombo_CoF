package client.gui.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.market.OnSaleItem;
import server.model.market.Soldable;

public class ItemProperty {
	private StringProperty seller;
	private IntegerProperty price;
	private Soldable item;
	
	/**
	 * Create a new ItemProperty with the given OnSaleItem
	 */
	public ItemProperty(OnSaleItem onSale) {
		this.price = new SimpleIntegerProperty(onSale.getPrice());
		this.seller = new SimpleStringProperty(onSale.getOwner().getName());
		
		// the items are stored with the server classes.
		// otherwhise, it would be impossible in some case
		// to distinguish an object from another one
		// e.g assistants from nobility points
		item = onSale.getItem();
	}

	/**
	 * @return the owner's name
	 */
	public StringProperty owner() {
		return this.seller;
	}
	
	/**
	 * @return the item's price
	 */
	public IntegerProperty price() {
		return this.price;
	}
	
	/**
	 * @return the item
	 */
	public Soldable getItem() {
		return this.item;
	}

}
