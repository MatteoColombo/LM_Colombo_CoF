package fx.view;

import java.io.IOException;
import java.util.List;

import client.model.CouncilProperty;
import client.model.PlayerProperty;
import client.model.SimpleBonus;
import client.model.SimpleCity;
import client.model.SimpleRegion;
import fx.MainApp;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.converter.NumberStringConverter;
import model.player.PermissionCard;

public class GameController {

	private MainApp mainApp;

	@FXML
	private Label playerNameLabel;

	@FXML
	private AnchorPane mapPane;

	@FXML
	private HBox kingCouncilBox;

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
		initLabels();
		initButtons();
		initOpponentsPanes();
		initPoliticTable();
		initMap();
		initConnections();
		initCouncils();
		initBoardRewards();
	}

	// --------------------DUMMY ACTIONS--------------------
	@FXML
	private void handleTestAction() throws IOException {
		mainApp.sendMsg("slide -council 1 -color pink");
	}

	@FXML
	private void handleTestAction2() throws IOException {
		mainApp.sendMsg("slide -council 1 -color blue");
	}

	@FXML
	private void handleTestAction3() throws IOException {
		mainApp.sendMsg("slide -council k -color black");
	}

	@FXML
	private void handleTestAction4() throws IOException {
		mainApp.sendMsg("slide -council 1 -color white");
	}
	// ------------------------------------------------------

	@FXML
	private void handlePass() throws IOException {
		mainApp.sendMsg("end");
		mainApp.getLocalModel().endOfTurn();
	}

	private void initButtons() {
		Bindings.bindBidirectional(mainActionButton1.disableProperty(),
				mainApp.getLocalModel().getMyPlayerData().canNotDoMainAction());
		Bindings.bindBidirectional(mainActionButton2.disableProperty(),
				mainApp.getLocalModel().getMyPlayerData().canNotDoMainAction());
		Bindings.bindBidirectional(mainActionButton3.disableProperty(),
				mainApp.getLocalModel().getMyPlayerData().canNotDoMainAction());
		Bindings.bindBidirectional(mainActionButton4.disableProperty(),
				mainApp.getLocalModel().getMyPlayerData().canNotDoMainAction());
		Bindings.bindBidirectional(sideActionButton1.disableProperty(),
				mainApp.getLocalModel().getMyPlayerData().canNotDoSideAction());
		Bindings.bindBidirectional(sideActionButton2.disableProperty(),
				mainApp.getLocalModel().getMyPlayerData().canNotDoSideAction());
		Bindings.bindBidirectional(sideActionButton3.disableProperty(),
				mainApp.getLocalModel().getMyPlayerData().canNotDoSideAction());
		Bindings.bindBidirectional(sideActionButton4.disableProperty(),
				mainApp.getLocalModel().getMyPlayerData().canNotDoSideAction());
	}

	private void initLabels() {
		PlayerProperty myData = mainApp.getLocalModel().getMyPlayerData();
		NumberStringConverter nsc = new NumberStringConverter();
		Bindings.bindBidirectional(assistantsLabel.textProperty(), myData.assistantsProperty(), nsc);
		Bindings.bindBidirectional(coinsLabel.textProperty(), myData.coinsProperty(), nsc);
		Bindings.bindBidirectional(nobilityLabel.textProperty(), myData.nobilityProperty(), nsc);
		Bindings.bindBidirectional(victoryLabel.textProperty(), myData.victoryProperty(), nsc);
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
		NumberStringConverter nsc = new NumberStringConverter();
		int myIndex = mainApp.getLocalModel().getMyIndex();
		for (int i = players.size() - 1; i >= 0; i--) {
			if (i != myIndex) {
				try {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(MainApp.class.getResource("/fxml/OpponentPane.fxml"));
					AnchorPane pane = (AnchorPane) loader.load();
					opponentsBox.getChildren().add(pane);
					((Labeled) pane.lookup("#nameLabel")).textProperty().set(players.get(i).getName());
					Bindings.bindBidirectional(((Labeled) pane.lookup("#victoryLabel")).textProperty(),
							players.get(i).victoryProperty(), nsc);
					Bindings.bindBidirectional(((Labeled) pane.lookup("#coinsLabel")).textProperty(),
							players.get(i).coinsProperty(), nsc);
					Bindings.bindBidirectional(((Labeled) pane.lookup("#assistantsLabel")).textProperty(),
							players.get(i).assistantsProperty(), nsc);
					Bindings.bindBidirectional(((Labeled) pane.lookup("#nobilityLabel")).textProperty(),
							players.get(i).nobilityProperty(), nsc);
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

					Circle king = (Circle) innerPane.lookup("#kingCircle");
					Bindings.bindBidirectional(king.visibleProperty(), sc.hasKing());

					HBox bonusBox = (HBox) innerPane.lookup("#bonusBox");
					for (SimpleBonus sb : sc.getBonuses()) {

						FXMLLoader innerLoader = new FXMLLoader();
						innerLoader.setLocation(MainApp.class.getResource("/fxml/Bonus.fxml"));
						Pane bonusPane = (Pane) innerLoader.load();

						ImageView bonusImage = (ImageView) bonusPane.lookup("#bonusImage");
						String bonusPath = sb.getImagePath();
						Image bonus = new Image(MainApp.class.getResource(bonusPath).toString());
						bonusImage.setImage(bonus);

						Label amountLabel = (Label) bonusPane.lookup("#amountLabel");
						amountLabel.setText(String.valueOf(sb.getAmount()));

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

	private void initCouncils() {
		CouncilProperty kingCouncil = mainApp.getLocalModel().getMap().getKingCouncil();
		for (StringProperty color : kingCouncil.colors()) {
			Rectangle councilor = generateCouncilor(color.get());
			// TODO check if works
			color.addListener((observable, oldValue, newValue) -> councilor.setFill(Color.valueOf(newValue)));
			kingCouncilBox.getChildren().add(councilor);
		}

		List<SimpleRegion> regions = mainApp.getLocalModel().getMap().getRegions();
		int numberOfRegions = regions.size();

		for (int i = 0; i < numberOfRegions; i++) {

			HBox councilBox = (HBox) mapPane.lookup("#councilBox" + String.valueOf(i));
			for (StringProperty color : regions.get(i).getCouncil().colors()) {

				Rectangle councilor = generateCouncilor(color.get());
				color.addListener((observable, oldValue, newValue) -> councilor.setFill(Color.valueOf(newValue)));
				councilBox.getChildren().add(councilor);
			}
		}
	}

	private Rectangle generateCouncilor(String hexColor) {
		Rectangle councilor = new Rectangle();
		councilor.setWidth(25.0);
		councilor.setHeight(50.0);
		councilor.setFill(Color.valueOf(hexColor));
		councilor.setStroke(Color.SILVER);
		councilor.setStrokeWidth(2.0);
		return councilor;
	}

	private void initBoardRewards() {
		NumberStringConverter nsc = new NumberStringConverter();
		Bindings.bindBidirectional(kingRewardLabel.textProperty(), mainApp.getLocalModel().getMap().kingBonus(), nsc);
	
		List<SimpleRegion> regions = mainApp.getLocalModel().getMap().getRegions();
		int numberOfRegions = regions.size();
		
		for(int i = 0; i < numberOfRegions; i++) {
			Labeled regionBonus = (Labeled) mapPane.lookup("#regionBonusLabel" + i);
			regionBonus.setText(String.valueOf(regions.get(i).getConquerBonus()));
			Bindings.bindBidirectional(regionBonus.visibleProperty(), regions.get(i).notTaken());
		}
		
	}
}
