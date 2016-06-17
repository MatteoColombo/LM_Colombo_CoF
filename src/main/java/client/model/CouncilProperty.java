package client.model;

import java.awt.Color;

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
	private ObservableList<StringProperty> councilorsColors;
	
	public CouncilProperty(Council council) {
		councilorsColors = FXCollections.observableArrayList();
		for(Color c: council.getCouncilorsColor()) {
			String hexColor = ColorConverter.awtToWeb(c);
			councilorsColors.add(new SimpleStringProperty(hexColor));
		}
	}
	
	public ObservableList<StringProperty> colors() {
		return this.councilorsColors;
	}
	
	public void set(Council council) {
		councilorsColors.clear();
		for(Color c: council.getCouncilorsColor()) {
			councilorsColors.add(new SimpleStringProperty(ColorConverter.awtToWeb(c)));
		}
	}
}
