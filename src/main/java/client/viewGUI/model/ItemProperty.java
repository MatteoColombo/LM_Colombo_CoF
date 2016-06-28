package client.viewGUI.model;

import java.awt.Color;

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
	
	public ItemProperty(OnSaleItem onSale) {
		this.price = new SimpleIntegerProperty(onSale.getPrice());
		this.seller = new SimpleStringProperty(onSale.getOwner().getName());
		
		// the items are stored with the server class.
		// otherwhise, it would be impossible in some case
		// to distinguish an object from another one
		// e.g assistants from nobility points
		item = onSale.getItem();
	}
	
	public StringProperty owner() {
		return this.seller;
	}
	
	public IntegerProperty price() {
		return this.price;
	}
	
	public Soldable getItem() {
		return this.item;
	}

}
