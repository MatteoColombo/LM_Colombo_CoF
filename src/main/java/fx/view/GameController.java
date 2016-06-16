package fx.view;

import java.io.IOException;

import client.model.PlayerProperty;
import fx.MainApp;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.util.converter.NumberStringConverter;
import model.market.GoodsBundle;
import model.player.PermissionCard;

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
	@FXML private TableColumn<ObjectProperty<PermissionCard>, String> permissionCardColumn;
	
	@FXML private Button mainActionButton1;
	@FXML private Button mainActionButton2;
	@FXML private Button mainActionButton3;
	@FXML private Button mainActionButton4;
	@FXML private Button sideActionButton1;
	@FXML private Button sideActionButton2;
	@FXML private Button sideActionButton3;
	@FXML private Button sideActionButton4;
	
	@FXML private TableView<GoodsBundle> marketTable;
	@FXML private TableColumn<GoodsBundle, String> marketSellerColumn;
	@FXML private TableColumn<GoodsBundle, String> goodsColumn;
	@FXML private TableColumn<GoodsBundle, String> priceColumn;
	
	@FXML private TextArea logger;
		
	public void setAll(MainApp mainApp) {
		this.mainApp = mainApp;
		politicCardsTable.setItems(mainApp.getLocalModel().getMyPlayerData().getPoliticCards());
		permissionCardsTable.setItems(mainApp.getLocalModel().getMyPlayerData().getPermissions());
		
		PlayerProperty myData = mainApp.getLocalModel().getMyPlayerData();
		
		NumberStringConverter nsc = new NumberStringConverter();
        Bindings.bindBidirectional(assistantsLabel.textProperty(), myData.assistantsProperty(), nsc);
        Bindings.bindBidirectional(coinsLabel.textProperty(), myData.coinsProperty(), nsc);
        Bindings.bindBidirectional(nobilityLabel.textProperty(), myData.nobilityProperty(), nsc);
        Bindings.bindBidirectional(victoryLabel.textProperty(), myData.victoryProperty(), nsc);
        
        Bindings.bindBidirectional(mainActionButton1.disableProperty(), mainApp.getLocalModel().canNotDoMainAction());
        Bindings.bindBidirectional(mainActionButton2.disableProperty(), mainApp.getLocalModel().canNotDoMainAction());
        Bindings.bindBidirectional(mainActionButton3.disableProperty(), mainApp.getLocalModel().canNotDoMainAction());
        Bindings.bindBidirectional(mainActionButton4.disableProperty(), mainApp.getLocalModel().canNotDoMainAction());
        Bindings.bindBidirectional(sideActionButton1.disableProperty(), mainApp.getLocalModel().canNotDoSideAction());
        Bindings.bindBidirectional(sideActionButton2.disableProperty(), mainApp.getLocalModel().canNotDoSideAction());
        Bindings.bindBidirectional(sideActionButton3.disableProperty(), mainApp.getLocalModel().canNotDoSideAction());
        Bindings.bindBidirectional(sideActionButton4.disableProperty(), mainApp.getLocalModel().canNotDoSideAction());
	}
	
	/*@FXML private void Initialize() {
			
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
					setStyle("-fx-background-color:" + item);
					}
				}
			};
		}
		);
	}*/
	
	@FXML private void handleTestAction() throws IOException {
		mainApp.sendMsg("slide -council 1 -color pink");
	}
	
}
