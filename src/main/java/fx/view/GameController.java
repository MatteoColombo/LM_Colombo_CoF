package fx.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import client.model.*;
import fx.MainApp;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.player.PermissionCard;

public class GameController {

	private static final int NOBILITY_START_X = 26;
	private static final int NOBILITY_START_Y = 775;
	private static final int NOBILITY_HEIGHT = 60;
	private static final double NOBILITY_STEP = 37.5;
	
	private MainApp mainApp;

	@FXML
	private Label nameLabel;

	@FXML
	private Rectangle myColor;
	
	@FXML
	private AnchorPane mapPane;

	@FXML
	private HBox kingCouncilBox;

	@FXML
	private FlowPane councilorPool;
	
	@FXML
	private Label kingRewardLabel;
	@FXML
	private Label goldRewardLabel;
	@FXML
	private Label silverRewardLabel;
	@FXML
	private Label bronzeRewardLabel;
	@FXML
	private Label sapphireRewardLabel;

	@FXML
	private Label victoryLabel;
	@FXML
	private Label coinsLabel;
	@FXML
	private Label assistantsLabel;
	@FXML
	private Label nobilityLabel;

	@FXML
	private TableView<StringProperty> politicCardsTable;
	@FXML
	private TableColumn<StringProperty, String> politicCardsColumn;

	@FXML
	private TableView<ObjectProperty<PermissionCard>> permissionCardsTable;
	@FXML
	private TableColumn<ObjectProperty<PermissionCard>, String> permissionCardsColumn;

	@FXML
	private Button mainActionButton1;
	@FXML
	private Button mainActionButton2;
	@FXML
	private Button mainActionButton3;
	@FXML
	private Button mainActionButton4;
	@FXML
	private Button sideActionButton1;
	@FXML
	private Button sideActionButton2;
	@FXML
	private Button sideActionButton3;
	@FXML
	private Button sideActionButton4;

	@FXML
	private VBox opponentsBox;

	@FXML
	private TextArea logger;

	public void setAll(MainApp mainApp) {
		this.mainApp = mainApp;
		initMyData();
		initOpponentsPanes();
		initPoliticTable();
		initMap();
		initConnections();
		initCouncils();
		initBoardRewards();
		initNobility();
		initPermissions();
		initCouncilorPool();
	}


	@FXML
	private void handleSlideCouncil() throws IOException {	
	}

	@FXML
	private void handleBuyPermission() throws IOException {
	}

	@FXML
	private void handleBuildEmporium() throws IOException {
	}

	@FXML
	private void handleBuildWithKing() throws IOException {
	}
	
	@FXML
	private void handleBuyAssistant() throws IOException {
		mainApp.sendMsg("assistant");
	}
	
	@FXML
	private void handleShuffle() throws IOException {
	}
	
	@FXML
	private void handleExtraAction() throws IOException {
		mainApp.sendMsg("extra");
	}
	
	@FXML
	private void handleSlideSide() throws IOException {
		
	}

	@FXML
	private void handlePass() throws IOException {
		mainApp.sendMsg("end");
	}

	private void initMyData() {
		PlayerProperty myData = mainApp.getLocalModel().getMyPlayerData();
		
		nameLabel.setText(myData.getName());
		myColor.setFill(myData.getColor());
		assistantsLabel.textProperty().bind(myData.assistantsProperty().asString());
		coinsLabel.textProperty().bind(myData.coinsProperty().asString());
		nobilityLabel.textProperty().bind(myData.nobilityProperty().asString());
		victoryLabel.textProperty().bind(myData.victoryProperty().asString());
		
		mainActionButton1.disableProperty().bind(myData.canNotDoMainAction());
		mainActionButton2.disableProperty().bind(myData.canNotDoMainAction());
		mainActionButton3.disableProperty().bind(myData.canNotDoMainAction());
		mainActionButton4.disableProperty().bind(myData.canNotDoMainAction());
		sideActionButton1.disableProperty().bind(myData.canNotDoSideAction());
		sideActionButton2.disableProperty().bind(myData.canNotDoSideAction());
		sideActionButton3.disableProperty().bind(myData.canNotDoSideAction());
		sideActionButton4.disableProperty().bind(myData.canNotDoSideAction());
	}

	private void initPoliticTable() {
		politicCardsTable.setItems(mainApp.getLocalModel().getMyPlayerData().getPoliticCards());
		politicCardsColumn.setCellValueFactory(cell -> cell.getValue());
		politicCardsColumn.setCellFactory(column -> {
			return new TableCell<StringProperty, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setStyle("");
					} else {
						setStyle("-fx-background-image: url('"
								+ GameController.class.getResource(PlayerProperty.getPoliticCardsImages().get(item))
								+ "'); -fx-background-size:cover;");
					}
				}
			};
		});
	}

	private void initOpponentsPanes() {
		ObservableList<PlayerProperty> players = mainApp.getLocalModel().getPlayers();
		int myIndex = mainApp.getLocalModel().getMyIndex();
		for (int i = players.size() - 1; i >= 0; i--) {
			if (i != myIndex) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainApp.class.getResource("/fxml/OpponentPane.fxml"));
					AnchorPane pane = loader.load();
					opponentsBox.getChildren().add(pane);
					((Labeled) pane.lookup("#nameLabel")).textProperty().set(players.get(i).getName());
					((Rectangle) pane.lookup("#colorRectangle")).setFill(players.get(i).getColor());
					
					((Labeled) pane.lookup("#victoryLabel")).textProperty().bind(players.get(i).victoryProperty().asString());
					((Labeled) pane.lookup("#coinsLabel")).textProperty().bind(players.get(i).coinsProperty().asString());
					((Labeled) pane.lookup("#assistantsLabel")).textProperty().bind(players.get(i).assistantsProperty().asString());
					((Labeled) pane.lookup("#nobilityLabel")).textProperty().bind(players.get(i).nobilityProperty().asString());
					
				} catch (IOException e) {
					logger.appendText(e.getMessage());
				}
			}
		}
	}

	private void initMap() {
		for (SimpleRegion r : mainApp.getLocalModel().getMap().getRegions()) {
			for (SimpleCity sc : r.getCities()) {

				try {
					AnchorPane rewardPane = (AnchorPane) mapPane.lookup("#" + sc.getName().toLowerCase());

					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainApp.class.getResource("/fxml/City.fxml"));
					AnchorPane innerPane = (AnchorPane) loader.load();

					ImageView cityImage = (ImageView) innerPane.lookup("#cityImage");
					String cityPath = sc.getImagePath();
					Image city = new Image(MainApp.class.getResource(cityPath).toString());
					cityImage.setImage(city);

					Label cityName = (Label) innerPane.lookup("#cityName");
					cityName.setText(sc.getName());

					Node king = innerPane.lookup("#king");
					king.visibleProperty().bind(sc.hasKing());

					HBox bonusBox = (HBox) innerPane.lookup("#bonusBox");
					for (SimpleBonus sb : sc.getBonuses()) {
						
						Pane bonusPane = generateBonus(sb);
						bonusBox.getChildren().add(bonusPane);
					}
					rewardPane.getChildren().add(innerPane);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void initConnections() {
		for (SimpleRegion r : mainApp.getLocalModel().getMap().getRegions()) {
			for (SimpleCity sc : r.getCities()) {
				AnchorPane cityPane = (AnchorPane) mapPane.lookup("#" + sc.getName().toLowerCase());

				double startx = cityPane.getLayoutX() + cityPane.getWidth();
				double starty = cityPane.getLayoutY() + cityPane.getHeight();

				for (String connected : sc.getConnections()) {

					AnchorPane connectedPane = (AnchorPane) mapPane.lookup("#" + connected.toLowerCase());

					double endx = connectedPane.getLayoutX() + connectedPane.getWidth();
					double endy = connectedPane.getLayoutY() + connectedPane.getHeight();

					Line path = new Line();
					path.setStrokeWidth(12.0);
					path.setStroke(Color.SADDLEBROWN);

					path.setStartX(startx);
					path.setStartY(starty);
					path.setEndX(endx);
					path.setEndY(endy);
					// add the line behind all cities, but fronter than the map
					// image in position 0
					mapPane.getChildren().add(1, path);
				}
			}
		}
	}
	
	/**
	 * 
	 */
	private void initCouncils() {
		CouncilProperty kingCouncil = mainApp.getLocalModel().getMap().getKingCouncil();
		initCouncil(kingCouncilBox, kingCouncil);

		List<SimpleRegion> regions = mainApp.getLocalModel().getMap().getRegions();

		for (int i = 0; i < regions.size(); i++) {

			HBox councilBox = (HBox) mapPane.lookup("#councilBox" + String.valueOf(i));
			initCouncil(councilBox, regions.get(i).getCouncil());
		}
	}

	private void initCouncil(HBox location, CouncilProperty council) {
		for (StringProperty color : council.colors()) {
			Rectangle councilor = generateCouncilor(color.get());
	
			color.addListener((observable, oldValue, newValue) -> {
				
				councilor.setFill(Color.valueOf(newValue));
				
				IntegerProperty oldColor = mainApp.getLocalModel().getMap().getCouncilorPool().get(oldValue);
				oldColor.set(oldColor.get() + 1);
				
				IntegerProperty newColor = mainApp.getLocalModel().getMap().getCouncilorPool().get(newValue);
				newColor.set(newColor.get() - 1);
				}
			);	
			location.getChildren().add(councilor);
		}
		
		location.setOnDragOver(event -> {
			if (event.getGestureSource() != location &&
	                event.getDragboard().hasString()) {
				location.setEffect(new Bloom());
	            event.acceptTransferModes(TransferMode.MOVE);
	        }
	        event.consume();
		});
		
		location.setOnDragExited(event -> {
			location.setEffect(null);
		});
		
		location.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			
			if(db.hasString()) {
				if(location.getId().equals("kingCouncilBox")) {
					mainApp.sendMsg("slide -council k -color " + db.getString());
				} else {
					String id = location.getId();
					int index = Integer.valueOf(id.substring(id.length()-1));
					index++;
					mainApp.sendMsg("slide -council " + index + " -color " + db.getString());
				}
			}
		});
	}

	private void initCouncilorPool() {
		mainApp.getLocalModel().getMap().initCouncilorPool();
		
		Map<String, IntegerProperty> pool = mainApp.getLocalModel().getMap().getCouncilorPool();
		for(String hexColor: pool.keySet()) {
			
			Rectangle councilor = generateCouncilor(hexColor);
			councilor.setHeight(councilor.getWidth());
	
			councilor.setOnDragDetected(event -> {
				if(pool.get(hexColor).get() > 0) {
					Dragboard db = councilor.startDragAndDrop(TransferMode.ANY);
					
					ClipboardContent content = new ClipboardContent();
					content.putString(councilor.getId());
					db.setContent(content);
			        event.consume();
				}
			});
			
			Label amount = new Label();
			amount.textProperty().bind(pool.get(hexColor).asString());
			
			amount.setStyle("-fx-background-color: black;");
			
			Pane councilorPane = new Pane();
			councilorPane.getChildren().add(councilor);
			councilorPane.getChildren().add(amount);
	
			councilorPool.getChildren().add(councilorPane);
		}
	}


	private Rectangle generateCouncilor(String hexColor) {
		Rectangle councilor = new Rectangle();
		councilor.setWidth(25.0);
		councilor.setHeight(50.0);
		councilor.setFill(Color.valueOf(hexColor));
		councilor.setStroke(Color.SILVER);
		councilor.setStrokeWidth(2.0);
		councilor.setId(CouncilProperty.getColorName(hexColor));
		return councilor;
	}
	
	private Pane generateBonus(SimpleBonus sb) throws IOException {
		FXMLLoader innerLoader = new FXMLLoader();
		innerLoader.setLocation(MainApp.class.getResource("/fxml/Bonus.fxml"));
		Pane bonusPane = (Pane) innerLoader.load();

		ImageView bonusImage = (ImageView) bonusPane.lookup("#bonusImage");
		String bonusPath = sb.getImagePath();
		Image bonus = new Image(MainApp.class.getResource(bonusPath).toString());
		bonusImage.setImage(bonus);

		Label amountLabel = (Label) bonusPane.lookup("#amountLabel");
		amountLabel.setText(String.valueOf(sb.getAmount()));
		return bonusPane;
	}

	private void initBoardRewards() {
		
		IntegerProperty kingBonus = mainApp.getLocalModel().getMap().kingBonus();
		kingRewardLabel.textProperty().bind(kingBonus.asString());
		kingRewardLabel.visibleProperty().bind(kingBonus.greaterThan(0));
				
		List<SimpleRegion> regions = mainApp.getLocalModel().getMap().getRegions();
		int numberOfRegions = regions.size();
		
		for(int i = 0; i < numberOfRegions; i++) {
			Labeled regionBonus = (Labeled) mapPane.lookup("#regionBonusLabel" + i);
			
			regionBonus.textProperty().bind(regions.get(i).conquerBonus().asString());
			regionBonus.visibleProperty().bind(regions.get(i).conquerBonus().greaterThan(0));			
		}
		
		Map<String, IntegerProperty> colorRewards = mainApp.getLocalModel().getMap().getColorBonuses();
		
		sapphireRewardLabel.textProperty().bind(colorRewards.get("#2268df").asString());
		sapphireRewardLabel.visibleProperty().bind(colorRewards.get("#2268df").greaterThan(0));
		
		goldRewardLabel.textProperty().bind(colorRewards.get("#ffd700").asString());
		goldRewardLabel.visibleProperty().bind(colorRewards.get("#ffd700").greaterThan(0));
		
		silverRewardLabel.textProperty().bind(colorRewards.get("#008000").asString());
		silverRewardLabel.visibleProperty().bind(colorRewards.get("#008000").greaterThan(0));
		
		bronzeRewardLabel.textProperty().bind(colorRewards.get("#f44343").asString());
		bronzeRewardLabel.visibleProperty().bind(colorRewards.get("#f44343").greaterThan(0));
	}
	
	private void initNobility() {
		
		List<SimpleNobilityCell> track = mainApp.getLocalModel().getMap().getNobilityTrack();
		for(int i = 0; i < track.size(); i++ ) {
			if(track.get(i) == null) {
				continue;
			}
			List<SimpleBonus> bonuses = track.get(i).getBonuses();
			for(int j = 0; j < bonuses.size(); j++) {
				try {
					Pane bonusPane = generateBonus(bonuses.get(j));
					bonusPane.setLayoutX(NOBILITY_START_X - 15 + i*NOBILITY_STEP);
					bonusPane.setLayoutY(NOBILITY_START_Y + j*NOBILITY_HEIGHT/bonuses.size());						
					mapPane.getChildren().add(bonusPane);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		List<PlayerProperty> players = mainApp.getLocalModel().getPlayers();
		int totalPlayers = players.size();
		for(int i = 0; i < totalPlayers; i++) {
			Circle c = new Circle();
			c.setFill(players.get(i).getColor());
			c.setRadius(15.0);
			c.setCenterY(NOBILITY_START_Y + i*NOBILITY_HEIGHT/totalPlayers);
			c.centerXProperty().bind(players.get(i).nobilityProperty().multiply(NOBILITY_STEP).add(NOBILITY_START_X));
			mapPane.getChildren().add(c);
		}
	}
	
	private void initPermissions() {
		List<SimpleRegion> regions = mainApp.getLocalModel().getMap().getRegions();
		
		for(int i = 0; i < regions.size(); i++) {
			PermissionProperty[] permissions = regions.get(i).getPermissions();
			
			for(int j = 0; j < permissions.length; j++) {
				
				Pane outerPane = (Pane) mapPane.lookup("#permit" + String.valueOf(i) + "_" + String.valueOf(j));
				
				// generation
				AnchorPane innerPane = generatePermission(permissions[j]);
				// binding city Label 
				((Labeled) innerPane.lookup("#citiesLabel")).textProperty().bind(permissions[j].getCities());
				// binding bonuses
				permissions[j].getBonuses().addListener((ListChangeListener.Change<? extends SimpleBonus>  c) -> {
					System.out.println("entered in listener at GameController:406");
					for(SimpleBonus sb: c.getList()) {
						try {
							// reset all the bonus in the list, 
							// since the amount of bonus in the box may change
							// from card to card
							HBox bonusBox = (HBox) innerPane.lookup("#bonusBox");
							bonusBox.getChildren().clear();
							bonusBox.getChildren().add(generateBonus(sb));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}		
				});
				
				outerPane.getChildren().add(innerPane);
			}
		}
	}
	
	private AnchorPane generatePermission(PermissionProperty  pp) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/fxml/PermissionCard.fxml"));
			AnchorPane permissionPane = loader.load();
			
			HBox bonusBox = (HBox) permissionPane.lookup("#bonusBox");
			for(SimpleBonus sb: pp.getBonuses()) {
				bonusBox.getChildren().add(generateBonus(sb));
			}
			// this is the real return
			return permissionPane;
						
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// just cause without won't compile
		return null;
	}
}
