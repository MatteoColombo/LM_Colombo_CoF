package client.gui.view;

import client.gui.control.MainApp;
import client.gui.model.PermissionProperty;
import client.gui.model.PlayerProperty;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MarketController {
	@FXML
	private BorderPane borderPane;
	
	@FXML
	private ListView<PermissionProperty> permissionList;
	@FXML
	private ListView<String> politicList;
	@FXML
	private TextField assistantField;
	@FXML
	private TextField priceField;
	@FXML
	private Label assistantLabel;
	@FXML
	private ImageView assistantImage;
	@FXML
	private ImageView marketImage;
	
	
	private MainApp mainApp;
	private PlayerProperty myData;
	private Stage dialogStage;
	
	SnapshotParameters params = new SnapshotParameters();
	
	@FXML
	private void initialize() {
		permissionList.prefWidthProperty().bind(borderPane.widthProperty().divide(2));
		politicList.prefWidthProperty().bind(borderPane.widthProperty().divide(2));	
		assistantField.setText("");
		priceField.setText("");
		
		Collection.addNumericRestriction(assistantField);
		Collection.addNumericRestriction(priceField);
		
		params.setFill(Color.TRANSPARENT);
	}
	
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	public void setAll(MainApp mainApp) {
		this.mainApp = mainApp;
		this.myData = mainApp.getLocalModel().getMyPlayerData();
		initPermissionList();
		initAssistants();
		initPoliticList();
		initMarket();
		setExit();
	}
	
	public void initPoliticList() {
		politicList.setItems(myData.getPoliticCards());
		
		politicList.setCellFactory(listView -> new ListCell<String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setStyle(null);
					} else {	
						setStyle("-fx-background-image: url('"
								+ GameController.class.getResource(PlayerProperty.getPoliticCardsImages().get(item))
								+ "'); -fx-background-size:cover;");
					}	
					this.setOnDragDetected(event -> {
						Dragboard db = this.startDragAndDrop(TransferMode.ANY);
						ClipboardContent content = new ClipboardContent();
						content.putString("politic " + (this.getIndex()+1));
						db.setContent(content);
						db.setDragView(this.snapshot(params, null));
						event.consume();
					});
				}
			}
		);
	}
	
	public void initPermissionList() {
		permissionList.setItems(myData.getPermissions());
		
		permissionList.setCellFactory(listView -> new ListCell<PermissionProperty>() {
			@Override
			public void updateItem(PermissionProperty item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					AnchorPane permissionPane = Collection.permissionCard(item);
					setGraphic(permissionPane);
				}			
				this.setOnDragDetected(event -> {
					Dragboard db = this.startDragAndDrop(TransferMode.ANY);
					ClipboardContent content = new ClipboardContent();
					content.putString("permission " + (this.getIndex()+1));
					db.setContent(content);
					db.setDragView(this.getChildren().get(0).snapshot(null, null));
					event.consume();
				});
			}
		});
	}
	
	public void initAssistants() {
		assistantLabel.textProperty().bind(myData.assistantsProperty().asString());
		
		assistantImage.setOnDragDetected(event -> {
			
			if(!assistantField.getText().isEmpty()) {
				Dragboard db = assistantImage.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString("assistant " + assistantField.getText());
				db.setContent(content);
				db.setDragView(assistantImage.snapshot(params, null));
				event.consume();
			}		
		});
	}
	
	public void initMarket() {
		marketImage.setOnDragOver(event -> {
			marketImage.setEffect(new Glow());
			event.acceptTransferModes(TransferMode.MOVE);
			event.consume();
		});
		
		marketImage.setOnDragExited(event -> marketImage.setEffect(null));
		
		marketImage.setOnDragDropped(event -> {
			if("".equals(priceField.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("MISSING PRICE");
				alert.setHeaderText("Price Field is empty");
				alert.setContentText("Please insert price");
				alert.show();
			} else {
				Dragboard db = event.getDragboard();
				if(db.hasString()) {
					mainApp.sendMsg(db.getString() + " " + priceField.getText());
				}
			}
			
		});
	}
	
	private void setExit() {
		dialogStage.setOnCloseRequest(event -> mainApp.sendMsg("end"));
	}
}
