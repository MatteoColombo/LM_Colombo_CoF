package client.viewGUI.view;

import java.io.IOException;

import client.viewGUI.control.MainApp;
import client.viewGUI.model.CouncilProperty;
import client.viewGUI.model.PermissionProperty;
import client.viewGUI.model.PlayerProperty;
import client.viewGUI.model.SimpleBonus;
import client.viewGUI.model.SimpleCity;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.IntegerBinding;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Collection {
	
	public static AnchorPane permissionCard(PermissionProperty pp) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/PermissionCard.fxml"));
			AnchorPane permissionPane = loader.load();

			((Labeled) permissionPane.lookup("#citiesLabel")).textProperty().bind(pp.getCities());

			HBox bonusBox = (HBox) permissionPane.lookup("#bonusBox");
			for (SimpleBonus sb : pp.getBonuses()) {
				bonusBox.getChildren().add(bonus(sb));
			}
			
			pp.getBonuses().addListener((ListChangeListener.Change<? extends SimpleBonus> c) -> {
				// reset all the bonus in the list,
				// since the amount of bonus in the box may change
				// from card to card
				bonusBox.getChildren().clear();
				for (SimpleBonus sb : c.getList()) {
					try {			
						bonusBox.getChildren().add(Collection.bonus(sb));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			// this is the real return
			return permissionPane;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// just cause without won't compile
		return null;
	}
	
	public static Pane bonus(SimpleBonus sb) {
		FXMLLoader innerLoader = new FXMLLoader();
		innerLoader.setLocation(MainApp.class.getResource("/fxml/Bonus.fxml"));
		Pane bonusPane;
		try {
			bonusPane = (Pane) innerLoader.load();
			ImageView bonusImage = (ImageView) bonusPane.lookup("#bonusImage");
			String bonusPath = sb.getImagePath();
			Image bonus = new Image(MainApp.class.getResource(bonusPath).toString());
			bonusImage.setImage(bonus);

			Label amountLabel = (Label) bonusPane.lookup("#amountLabel");
			amountLabel.setText(String.valueOf(sb.getAmount()));
			return bonusPane;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Rectangle councilor(String hexColor) {
		Rectangle councilor = new Rectangle();
		councilor.setWidth(25.0);
		councilor.setHeight(50.0);
		councilor.setFill(Color.valueOf(hexColor));
		councilor.setStroke(Color.SILVER);
		councilor.setStrokeWidth(2.0);
		councilor.setId(CouncilProperty.getColorName(hexColor));
		return councilor;
	}
	
	public static AnchorPane city(SimpleCity sc) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("/fxml/City.fxml"));
		AnchorPane cityPane;
		try {
			cityPane = (AnchorPane) loader.load();
			ImageView cityImage = (ImageView) cityPane.lookup("#cityImage");
			String cityPath = sc.getImagePath();
			Image city = new Image(MainApp.class.getResource(cityPath).toString());
			cityImage.setImage(city);

			Label cityName = (Label) cityPane.lookup("#cityName");
			cityName.setText(sc.getName());
			
			FlowPane emporiumBox = (FlowPane) cityPane.lookup("#emporiumBox");
			sc.getEmporiums().addListener((ListChangeListener.Change<? extends Color> c) -> {
				emporiumBox.getChildren().clear();
				for(Color emporiumColor: sc.getEmporiums()) {
					Circle emporium = new Circle();
					emporium.setRadius(10.0);
					emporium.setFill(emporiumColor);
					emporiumBox.getChildren().add(emporium);
				}
			});
	
			return cityPane;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static AnchorPane opponent(PlayerProperty player) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/OpponentPane.fxml"));
			AnchorPane pane = loader.load();
			((Labeled) pane.lookup("#nameLabel")).textProperty().set(player.getName());
			((Rectangle) pane.lookup("#colorRectangle")).setFill(player.getColor());

			((Labeled) pane.lookup("#victoryLabel")).textProperty()
					.bind(player.victoryProperty().asString());
			((Labeled) pane.lookup("#coinsLabel")).textProperty()
					.bind(player.coinsProperty().asString());
			((Labeled) pane.lookup("#assistantsLabel")).textProperty()
					.bind(player.assistantsProperty().asString());
			((Labeled) pane.lookup("#nobilityLabel")).textProperty()
					.bind(player.nobilityProperty().asString());

			IntegerBinding permSizeProperty = Bindings.size(player.getPermissions());
			IntegerBinding politicSizeProperty = Bindings.size(player.getPoliticCards());
			
			((Labeled) pane.lookup("#permissionLabel")).textProperty().bind(permSizeProperty.asString());
			((Labeled) pane.lookup("#politicLabel")).textProperty().bind(politicSizeProperty.asString());
			return pane;
		} catch (IOException e) {
			// TODO log
			e.printStackTrace();
		}
		return null;
	}
}
