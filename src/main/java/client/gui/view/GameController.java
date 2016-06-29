package client.gui.view;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import client.gui.control.MainApp;
import client.gui.model.CouncilProperty;
import client.gui.model.ItemProperty;
import client.gui.model.PermissionProperty;
import client.gui.model.PlayerProperty;
import client.gui.model.SimpleBonus;
import client.gui.model.SimpleCity;
import client.gui.model.SimpleNobilityCell;
import client.gui.model.SimpleRegion;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Glow;
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
import server.model.market.Soldable;
import server.model.player.Assistants;
import server.model.player.PermissionCard;
import server.model.player.PoliticCard;
import util.ColorConverter;

/**
 * Game.fxml controller class
 * 
 * @author gianpaolobranca
 *
 */
public class GameController {

	// nobility scale parameters for positioning on the map
	private static final int NOBILITY_START_X = 26;
	private static final int NOBILITY_START_Y = 775;
	private static final int NOBILITY_HEIGHT = 60;
	private static final double NOBILITY_STEP = 37.5;

	// Game Status
	private static final String EMP = "emporium";
	private static final String KING = "king";
	private static final String SLIDE = "slide";
	private static final String PERM = "permission";
	private static final String SHUFFLE = "shuffle";
	private static final String SLIDE2 = "secondarySlide";
	private static final String DRAGK = "dragKing";
	private static final String CITY = " -city ";
	private static final String PERMISSION = " -permission";

	private MainApp mainApp;
	private PlayerProperty myData;

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
	private ListView<String> politicList;

	@FXML
	private ListView<PermissionProperty> permissionList;

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
	private TableView<ItemProperty> itemsTable;
	@FXML
	private TableColumn<ItemProperty, String> ownerColumn;
	@FXML
	private TableColumn<ItemProperty, String> priceColumn;
	@FXML
	private Pane itemPane;
	@FXML
	private Button buyButton;
	@FXML
	private Button endBuyButton;

	@FXML
	private TextArea logger;

	private String gameStatus = "";

	private Node kingOldPosition;
	private Node kingNewPosition;

	/**
	 * create a popup when something goes wrong
	 * 
	 * @param msg
	 *            the message to show in the alert popup
	 */
	public void showAlert(String msg) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("ERROR");
		alert.setHeaderText("Action not available");
		alert.setContentText(msg);
		alert.show();
	}

	/**
	 * change the game status
	 * 
	 * @param newStatus
	 */
	public void changeStatus(String newStatus) {
		gameStatus = newStatus;
	}

	/**
	 * log a message in the TextAreain the top-right corner of the screen
	 * 
	 * @param msg
	 */
	public void logMsg(String msg) {
		logger.appendText(msg + "\n");
	}

	/**
	 * show the sell window
	 */
	public void launchMarketSell() {
		mainApp.showMarket();
	}

	/**
	 * initialize the market table for buying
	 */
	private void initMarketBuy() {
		endBuyButton.setDisable(false);
		itemsTable.setItems(mainApp.getLocalModel().getMarket());
		ownerColumn.setCellValueFactory(cell -> cell.getValue().owner());
		priceColumn.setCellValueFactory(cell -> cell.getValue().price().asString());

		itemsTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showItemDetails(newValue));
	}

	public void setAll(MainApp mainApp) {
		this.mainApp = mainApp;
		this.myData = mainApp.getLocalModel().getMyPlayerData();
		initMyData();
		initOpponentsPanes();
		initPoliticList();
		initPermissionList();
		initMap();
		initRegionSymbols();
		initConnections();
		initCouncils();
		initBoardRewards();
		initNobility();
		initPermissions();
		initCouncilorPool();
		initMarketBuy();
	}

	@FXML
	private void handleSlideCouncil() throws IOException {
		resetKing();
		gameStatus = SLIDE;
	}

	@FXML
	private void handleBuyPermission() throws IOException {
		resetKing();
		gameStatus = PERM;
	}

	@FXML
	private void handleBuildEmporium() throws IOException {
		resetKing();
		gameStatus = EMP;
	}

	@FXML
	private void handleBuildWithKing() throws IOException {
		gameStatus = KING;
	}

	@FXML
	private void handleBuyAssistant() throws IOException {
		resetKing();
		mainApp.sendMsg("assistant");
	}

	@FXML
	private void handleShuffle() throws IOException {
		resetKing();
		gameStatus = SHUFFLE;
	}

	@FXML
	private void handleExtraAction() throws IOException {
		resetKing();
		mainApp.sendMsg("extra");
	}

	@FXML
	private void handleSlideSide() throws IOException {
		resetKing();
		gameStatus = SLIDE2;
	}

	@FXML
	private void handlePass() throws IOException {
		resetKing();
		mainApp.sendMsg("end");
	}

	@FXML
	private void showMarket() {
		mainApp.showMarket();
	}

	private void resetKing() {
		if (kingOldPosition != null) {
			kingNewPosition.setVisible(false);
			kingOldPosition.setVisible(true);
			kingOldPosition = null;
		}
	}

	/**
	 * initialize all the action buttons and the labels, binding them to the
	 * {@link PlayerProperty} of the user
	 */
	private void initMyData() {

		nameLabel.setText(myData.nameProperty().get());
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

	private void initPoliticList() {
		politicList.setItems(myData.getPoliticCards());
		politicList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		// allow drag of the selected cards when the game is in
		// "build emporium with king" (KING) or
		// "buy permission card" (PERM) status
		politicList.setOnDragDetected(event -> {
			if (KING.equals(gameStatus) || PERM.equals(gameStatus)) {
				Dragboard db = politicList.startDragAndDrop(TransferMode.ANY);

				ClipboardContent content = new ClipboardContent();
				String buffer = "";
				for (int i : politicList.getSelectionModel().getSelectedIndices()) {
					buffer += " " + (i + 1);
				}
				content.putString(buffer.substring(1));
				db.setContent(content);
				db.setDragView(new Image(this.getClass().getResource("/simboli/politic.png").toString()));
				event.consume();
			}
		});
		// populate the list. the card image is set as the cell background
		politicList.setCellFactory(column -> new ListCell<String>() {
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
			}
		});
	}

	private void initPermissionList() {
		permissionList.setItems(myData.getPermissions());

		permissionList.setCellFactory(listView -> new ListCell<PermissionProperty>() {
			@Override
			public void updateItem(PermissionProperty item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
					setText(null);
				} else {
					AnchorPane permissionPane = Collection.permissionCard(item);
					setGraphic(permissionPane);
				}
				// allow drag when the player want to build emporium
				this.setOnDragDetected(event -> {
					if (EMP.equals(gameStatus)) {
						Dragboard db = this.startDragAndDrop(TransferMode.ANY);
						ClipboardContent content = new ClipboardContent();
						content.putString("" + (this.getIndex() + 1));
						db.setContent(content);
						event.consume();
					}
				});
				// trigger with the special bonus "take the reward from a
				// permission card you have"
				this.setOnMouseClicked(event -> {
					if ("fromPermit".equals(gameStatus)) {
						mainApp.sendMsg("" + (this.getIndex() + 1));
					}
				});

				this.setOnMouseEntered(event -> {
					if ("fromPermit".equals(gameStatus)) {
						this.setEffect(new Glow());
					}
					event.consume();
				});

				this.setOnMouseExited(event -> {
					if (this.isDisabled()) {
						ColorAdjust grayscale = new ColorAdjust();
						grayscale.setSaturation(-1);
						this.setEffect(grayscale);
					} else {
						this.setEffect(null);
					}
				});
			}
		});
	}

	// generate a pane for each opponent
	private void initOpponentsPanes() {
		ObservableList<PlayerProperty> players = mainApp.getLocalModel().getPlayers();
		int myIndex = mainApp.getLocalModel().getMyIndex();
		for (int i = players.size() - 1; i >= 0; i--) {
			if (i != myIndex) {
				opponentsBox.getChildren().add(Collection.opponent(players.get(i)));
			}
		}
	}

	private void initMap() {
		for (SimpleRegion r : mainApp.getLocalModel().getMap().getRegions()) {

			for (SimpleCity sc : r.getCities()) {

				AnchorPane cityPane = (AnchorPane) mapPane.lookup("#" + sc.getName().toLowerCase());

				AnchorPane innerPane = Collection.city(sc);

				Node king = innerPane.lookup("#king");
				king.visibleProperty().bindBidirectional(sc.hasKing());
				// allow drop when the king is dragged over, or when the player
				// want to build
				// an emporium here
				cityPane.setOnDragOver(event -> {
					if (EMP.equals(gameStatus) || DRAGK.equals(gameStatus)) {
						cityPane.setEffect(new Glow());
						event.acceptTransferModes(TransferMode.MOVE);
					}
					event.consume();
				});

				cityPane.setOnDragExited(event -> cityPane.setEffect(null));

				cityPane.setOnDragDropped(event -> {
					// TODO not yet tested
					Dragboard db = event.getDragboard();
					if (DRAGK.equals(gameStatus)) {
						gameStatus = "king";
						kingNewPosition = king;
						king.setVisible(true);
					} else if (db.hasString() && EMP.equals(gameStatus)) {
						String action = gameStatus + CITY + cityPane.getId() + PERMISSION + db.getString();
						logger.appendText(action);
						mainApp.sendMsg(
								gameStatus + CITY + cityPane.getId() + PERMISSION + db.getString());
					}
				});
				// trigger when a player can take the Reward from this city with
				// the nobility bonus
				cityPane.setOnMouseClicked(event -> {
					if ("city".equals(gameStatus)) {
						mainApp.sendMsg(cityPane.getId());
					}
				});

				cityPane.setOnMouseEntered(event -> {
					if ("city".equals(gameStatus)) {
						cityPane.setEffect(new Glow());
					}
					event.consume();
				});

				cityPane.setOnMouseExited(event -> cityPane.setEffect(null));

				HBox bonusBox = (HBox) innerPane.lookup("#bonusBox");
				for (SimpleBonus sb : sc.getBonuses()) {

					Pane bonusPane = Collection.bonus(sb);
					bonusBox.getChildren().add(bonusPane);
				}
				cityPane.getChildren().add(innerPane);

				initKing(cityPane, king);
			}
		}
	}

	private void initKing(AnchorPane cityPane, Node king) {
		// TODO fix the king disappear bug
		king.setOnDragDetected(event -> {

			// allow dragging the king if the player want to buld emporium with
			// it
			Dragboard db = king.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			if (KING.equals(gameStatus)) {
				gameStatus = DRAGK;
				content.putString(KING);
				db.setContent(content);
				event.consume();
			}
		});

		king.setOnDragDone(event -> {
			if (kingOldPosition == null) {
				kingOldPosition = king;
			}
			king.setVisible(false);
		});

		king.setOnDragOver(event -> {
			if (KING.equals(gameStatus)) {
				king.setEffect(new Glow());
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		king.setOnDragExited(event -> king.setEffect(null));

		// trigger when a player drop politic cards over it, sending the action
		// to the server
		king.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			if (db.hasString()) {
				resetKing();
				mainApp.sendMsg(gameStatus + CITY + cityPane.getId() + " -cards " + db.getString());
			}
		});
	}

	// connect the cities, it work since the Anchorpane on the map has the same
	// id as the city linked
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

	private void initCouncils() {
		CouncilProperty kingCouncil = mainApp.getLocalModel().getMap().getKingCouncil();
		initCouncil(kingCouncilBox, kingCouncil);

		List<SimpleRegion> regions = mainApp.getLocalModel().getMap().getRegions();

		for (int i = 0; i < regions.size(); i++) {
			// the council has an id ending with the number of regions
			HBox councilBox = (HBox) mapPane.lookup("#councilBox" + i);
			initCouncil(councilBox, regions.get(i).getCouncil());
		}
	}

	private void initCouncil(HBox location, CouncilProperty council) {
		for (StringProperty color : council.colors()) {
			Rectangle councilor = Collection.councilor(color.get());
			// ensure that the label indicating the remaining councilors is
			// correct
			// without receiving the information from the server
			color.addListener((observable, oldValue, newValue) -> {

				councilor.setFill(Color.valueOf(newValue));

				IntegerProperty oldColor = mainApp.getLocalModel().getMap().getCouncilorPool().get(oldValue);
				oldColor.set(oldColor.get() + 1);

				IntegerProperty newColor = mainApp.getLocalModel().getMap().getCouncilorPool().get(newValue);
				newColor.set(newColor.get() - 1);
			});
			location.getChildren().add(councilor);
		}
		// allow drop is the player want to slide a council
		location.setOnDragOver(event -> {
			if (event.getGestureSource() != location && (SLIDE.equals(gameStatus) || SLIDE2.equals(gameStatus))) {
				location.setEffect(new Glow());
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});

		location.setOnDragExited(event -> location.setEffect(null));
		// send the action to the server
		location.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();

			if (db.hasString()) {
				if ("kingCouncilBox".equals(location.getId())) {
					mainApp.sendMsg(gameStatus + " -council k -color " + db.getString());
				} else {
					String id = location.getId();
					int index = Integer.parseInt(id.substring(id.length() - 1));
					mainApp.sendMsg(gameStatus + " -council " + (index + 1) + " -color " + db.getString());
				}
			}
		});
	}

	private void initCouncilorPool() {
		mainApp.getLocalModel().getMap().initCouncilorPool();

		Map<String, IntegerProperty> pool = mainApp.getLocalModel().getMap().getCouncilorPool();
		for (Entry<String, IntegerProperty> hexColor : pool.entrySet()) {

			Rectangle councilor = Collection.councilor(hexColor.getKey());
			councilor.setHeight(councilor.getWidth());

			councilor.setOnDragDetected(event -> {
				/*
				 * activate the drag only at two condition 1) SLIDE or
				 * "secondartSlide" is the current action wanted 2) there is at
				 * least one councilor of the selected color to drag from the
				 * poll
				 */
				if ((SLIDE.equals(gameStatus) || SLIDE2.equals(gameStatus)) && (hexColor.getValue().get() > 0)) {
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

	private void initBoardRewards() {

		IntegerProperty kingBonus = mainApp.getLocalModel().getMap().kingBonus();
		kingRewardLabel.textProperty().bind(kingBonus.asString());
		kingRewardLabel.visibleProperty().bind(kingBonus.greaterThan(0));

		List<SimpleRegion> regions = mainApp.getLocalModel().getMap().getRegions();
		int numberOfRegions = regions.size();

		for (int i = 0; i < numberOfRegions; i++) {
			Labeled regionBonus = (Labeled) mapPane.lookup("#regionBonusLabel" + i);

			regionBonus.textProperty().bind(regions.get(i).conquerBonus().asString());
			regionBonus.visibleProperty().bind(regions.get(i).conquerBonus().greaterThan(0));
		}

		Map<String, IntegerProperty> colorRewards = mainApp.getLocalModel().getMap().getColorBonuses();
		// if the bonuses are set to 0, the label became invisible
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
		for (int i = 0; i < track.size(); i++) {
			if (track.get(i) == null) {
				continue;
			}
			List<SimpleBonus> bonuses = track.get(i).getBonuses();
			for (int j = 0; j < bonuses.size(); j++) {
				Pane bonusPane = Collection.bonus(bonuses.get(j));
				bonusPane.setLayoutX(NOBILITY_START_X - 15 + i * NOBILITY_STEP);
				bonusPane.setLayoutY(NOBILITY_START_Y - 15 + j * NOBILITY_HEIGHT / bonuses.size());
				mapPane.getChildren().add(bonusPane);
			}
		}

		List<PlayerProperty> players = mainApp.getLocalModel().getPlayers();
		int totalPlayers = players.size();
		for (int i = 0; i < totalPlayers; i++) {
			Circle c = new Circle();
			c.setFill(players.get(i).getColor());
			c.setRadius(15.0);
			c.setCenterY(NOBILITY_START_Y + i * NOBILITY_HEIGHT / totalPlayers);
			// ensure that the circle moves as the nobility point rise
			// (very cool binding)
			c.centerXProperty().bind(players.get(i).nobilityProperty().multiply(NOBILITY_STEP).add(NOBILITY_START_X));
			mapPane.getChildren().add(c);
		}
	}

	private void initPermissions() {
		List<SimpleRegion> regions = mainApp.getLocalModel().getMap().getRegions();

		for (int i = 0; i < regions.size(); i++) {
			PermissionProperty[] permissions = regions.get(i).getPermissions();

			for (int j = 0; j < permissions.length; j++) {

				Pane outerPane = (Pane) mapPane.lookup("#permit" + i + "_" + j);
				// allow drop if a player want to buy a permission card
				outerPane.setOnDragOver(event -> {
					if (PERM.equals(gameStatus)) {
						outerPane.setEffect(new Glow());
						event.acceptTransferModes(TransferMode.MOVE);
					}
					event.consume();
				});

				outerPane.setOnDragExited(event -> outerPane.setEffect(null));
				// send the requested action to the server
				outerPane.setOnDragDropped(event -> {
					Dragboard db = event.getDragboard();
					if (db.hasString()) {
						String id = outerPane.getId();
						String info = id.substring(id.length() - 3);
						String card = info.substring(2);
						String region = info.substring(0, 1);
						int cardIndex = Integer.valueOf(card) + 1;
						int regionIndex = Integer.valueOf(region) + 1;
						mainApp.sendMsg(gameStatus + " -region " + regionIndex + PERMISSION + cardIndex
								+ " -cards " + db.getString());
					}
				});

				// generation
				AnchorPane innerPane = Collection.permissionCard(permissions[j]);

				outerPane.getChildren().add(innerPane);
				// can trigger when the player can take a free permission card
				outerPane.setOnMouseClicked(event -> {
					if ("takePermission".equals(gameStatus)) {
						String id = outerPane.getId();
						String info = id.substring(id.length() - 3);
						String card = info.substring(2);
						String region = info.substring(0, 1);
						int cardIndex = Integer.valueOf(card) + 1;
						int regionIndex = Integer.valueOf(region) + 1;
						mainApp.sendMsg(regionIndex + " " + cardIndex);
					}
				});

				outerPane.setOnMouseEntered(event -> {
					if ("takePermission".equals(gameStatus)) {
						outerPane.setEffect(new Glow());
					}
					event.consume();
				});

				outerPane.setOnMouseExited(event -> outerPane.setEffect(null));
			}
		}
	}

	private void initRegionSymbols() {
		int regionNumbers = mainApp.getLocalModel().getMap().getRegions().size();
		for (int i = 0; i < regionNumbers; i++) {
			Node regionSymbol = mapPane.lookup("#region" + i);

			// can trigger when a player want to shuffle this region
			regionSymbol.setOnMouseEntered(event -> {
				if (SHUFFLE.equals(gameStatus)) {
					regionSymbol.setEffect(new Bloom());
				}
				event.consume();
			});

			regionSymbol.setOnMouseExited(event -> regionSymbol.setEffect(null));

			regionSymbol.setOnMouseClicked(event -> {
				if (SHUFFLE.equals(gameStatus)) {
					int number = Integer.valueOf(regionSymbol.getId().substring(regionSymbol.getId().length() - 1));
					mainApp.sendMsg(gameStatus + " -region " + (number + 1));
				}
			});
		}
	}

	private void showItemDetails(ItemProperty item) {

		itemPane.getChildren().clear();
		buyButton.setDisable(false);

		Soldable itemOnSale = item.getItem();
		// show the item
		if (itemOnSale instanceof Assistants) {

			Image assistantImage = new Image(MainApp.class.getResource("/simboli/assistants.png").toString());
			ImageView assistant = new ImageView(assistantImage);
			assistant.fitWidthProperty().set(40);
			assistant.preserveRatioProperty().set(true);
			itemPane.getChildren().add(assistant);

			Label amount = new Label();
			amount.setText("x" + ((Assistants) itemOnSale).getAmount());
			itemPane.getChildren().add(amount);

		} else if (itemOnSale instanceof PoliticCard) {
			String politicColor;
			if (((PoliticCard) itemOnSale).isMultipleColor()) {
				politicColor = "multi";
			} else {
				politicColor = ColorConverter.awtToWeb(((PoliticCard) itemOnSale).getCardColor());
			}
			Image politicCardImage = new Image(PlayerProperty.getPoliticCardsImages().get(politicColor));
			ImageView politicCard = new ImageView(politicCardImage);
			politicCard.fitWidthProperty().set(100);
			politicCard.preserveRatioProperty().set(true);
			itemPane.getChildren().add(politicCard);

		} else if (itemOnSale instanceof PermissionCard) {

			PermissionProperty permission = new PermissionProperty((PermissionCard) itemOnSale);
			AnchorPane permissionPane = Collection.permissionCard(permission);
			itemPane.getChildren().add(permissionPane);
		}
	}

	@FXML
	private void handleBuy() {
		mainApp.sendMsg("" + (itemsTable.getSelectionModel().getSelectedIndex() + 1));
		buyButton.setDisable(true);
	}

	@FXML
	private void handleEndBuy() {
		mainApp.sendMsg("end");
	}
}
