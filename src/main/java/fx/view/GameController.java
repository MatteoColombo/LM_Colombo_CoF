package fx.view;

import java.io.IOException;

import client.model.PlayerProperty;
import fx.MainApp;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.converter.NumberStringConverter;
import model.market.GoodsBundle;
import model.player.PermissionCard;
import model.player.PoliticCard;

public class GameController {
	
	MainApp mainApp;
	
	@FXML private Label playerNameLabel;
	
	@FXML private Label victoryLabel;
	@FXML private Label coinsLabel;
	@FXML private Label assistantsLabel;
	@FXML private Label nobilityLabel;
	
	@FXML private TableView<StringProperty> politicCardsTable;
	@FXML private TableColumn<StringProperty, String> politicCardColumn;
	
	@FXML private TableView<ObjectProperty<PermissionCard>> permissionCardsTable;
	@FXML private TableColumn<ObjectProperty<PermissionCard>, Color> permissionCardColumn;
	
	@FXML private TableView<GoodsBundle> marketTable;
	@FXML private TableColumn<GoodsBundle, String> marketSellerColumn;
	@FXML private TableColumn<GoodsBundle, String> goodsColumn;
	@FXML private TableColumn<GoodsBundle, String> priceColumn;
	
	@FXML private TextArea logger;
	
	private ObservableList<StringProperty> test = FXCollections.observableArrayList();
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		test.add(new SimpleStringProperty("blue"));
		test.add(new SimpleStringProperty("green"));
		politicCardsTable.setItems(test);
		//politicCardsTable.setItems(mainApp.getLocalModel().getMyPlayerData().getPoliticCards());
		//permissionCardsTable.setItems(mainApp.getLocalModel().getMyPlayerData().getPermissions());
	}
	
	@FXML private void Initialize() {
		PlayerProperty myData = mainApp.getLocalModel().getMyPlayerData();
		
		coinsLabel.setText("" + myData.getCoins());
        Bindings.bindBidirectional(coinsLabel.textProperty(), myData.coinsProperty(), new NumberStringConverter());
		victoryLabel.setText("" + myData.getVictory());
        Bindings.bindBidirectional(victoryLabel.textProperty(), myData.victoryProperty(), new NumberStringConverter());
		assistantsLabel.setText("" + myData.getAssistants());
        Bindings.bindBidirectional(assistantsLabel.textProperty(), myData.assistantsProperty(), new NumberStringConverter());
		nobilityLabel.setText("" + myData.getNobility());
        Bindings.bindBidirectional(nobilityLabel.textProperty(), myData.nobilityProperty(), new NumberStringConverter());
			
		politicCardColumn.setCellFactory(cell -> {
			return new TableCell<StringProperty, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if(empty) {
						setText(null);
						setStyle("");
					} else {
					super.updateItem(item, empty);
					setText(item);
					setStyle("-fx-background-color:green");
					}
				}
			};
		}
		);
	}
	
	@FXML private void handleTestAction() throws IOException {
		mainApp.sendMsg("slide -council 1 -color pink");
	}
	
}
