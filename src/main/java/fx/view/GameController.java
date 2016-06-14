package fx.view;

import fx.MainApp;
import javafx.beans.property.ObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import model.market.GoodsBundle;
import model.player.PermissionCard;
import model.player.PoliticCard;

public class GameController {
	
	MainApp mainApp;
	
	@FXML private Label playerNameLabel;
	
	@FXML private Label victoryPointsLabel;
	@FXML private Label coinsLabel;
	@FXML private Label assistantsLabel;
	@FXML private Label nobilityLabel;
	
	@FXML private TableView<ObjectProperty<PoliticCard>> politicCardsTable;
	@FXML private TableColumn<ObjectProperty<PoliticCard>, Color> politicCardColumn;
	
	@FXML private TableView<ObjectProperty<PermissionCard>> permissionCardsTable;
	@FXML private TableColumn<ObjectProperty<PermissionCard>, String> permissionCardColumn;
	
	@FXML private TableView<GoodsBundle> marketTable;
	@FXML private TableColumn<GoodsBundle, String> marketSellerColumn;
	@FXML private TableColumn<GoodsBundle, String> goodsColumn;
	@FXML private TableColumn<GoodsBundle, String> priceColumn;
	
	@FXML private TextArea logger;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		
		politicCardsTable.setItems(mainApp.getLocalModel().getMyPlayerData().getPoliticCards());
		permissionCardsTable.setItems(mainApp.getLocalModel().getMyPlayerData().getPermissions());
	}
	
	@FXML private void Initialize() {

	}
	
}
