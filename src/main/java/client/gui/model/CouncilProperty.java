package client.gui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import server.model.board.council.Council;
import util.ColorConverter;
/**
 * this class contains a simplified council for the view, 
 * with just the string representation of the color
 * @author gianpaolobranca
 *
 */
public class CouncilProperty {
	
	private static Map<String, String> colorName;
	static {
		colorName = new HashMap<>();
		colorName.put("#ffffff", "white");
		colorName.put("#000000", "black"); 
		colorName.put("#ff9900", "orange"); 
		colorName.put("#0066ff", "blue"); 
		colorName.put("#ff99cc", "pink"); 
		colorName.put("#cc33ff", "violet"); 
	}
	
	private List<StringProperty> councilorsColors;
	
	/**
	 * Create a new empty CouncilProperty
	 */
	public CouncilProperty() {
		councilorsColors = new ArrayList<>();
	}
	/**
	 * @return the list of councilor's color as a list of stringproperty
	 */
	public List<StringProperty> colors() {
		return this.councilorsColors;
	}
		
	/**
	 * initialize the size of the council
	 */
	public void initCouncil(int size) {
		for(int i = 0; i < size; i++) {
			councilorsColors.add(new SimpleStringProperty(""));
		}
	}
	
	/**
	 * set the councilProperty
	 * @param council the server class for setting
	 */
	public void set(Council council) {
		for(int i = 0; i < councilorsColors.size(); i++) {
			String newHexColor = ColorConverter.awtToWeb(council.getCouncilorsColor().get(i));
			councilorsColors.get(i).set(newHexColor);
		}
	}
	
	/**
	 * @param color the hex representation of the color (e.g: "#ffffff")
	 * @return his name in natural language, or null if not found
	 */
	public static String getColorName(String color) {
		return colorName.get(color);
	}
}
