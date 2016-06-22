package client.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.board.council.Council;
import util.ColorConverter;
/**
 * this class contains a simplified council for the view, 
 * with just the string representation of the color
 * @author gianpaolobranca
 *
 */
public class CouncilProperty {
	private List<StringProperty> councilorsColors;
	
	public CouncilProperty() {
		councilorsColors = new ArrayList<>();
	}
	
	public List<StringProperty> colors() {
		return this.councilorsColors;
	}
		
	public void initCouncil(int size) {
		for(int i = 0; i < size; i++) {
			councilorsColors.add(new SimpleStringProperty(""));
		}
	}
	
	public void set(Council council) {
		for(int i = 0; i < councilorsColors.size(); i++) {
			String newHexColor = ColorConverter.awtToWeb((council.getCouncilorsColor().get(i)));
			councilorsColors.get(i).set(newHexColor);
		}
	}
}
