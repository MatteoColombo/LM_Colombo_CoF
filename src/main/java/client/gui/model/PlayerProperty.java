package client.gui.model;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import server.model.player.PermissionCard;
import server.model.player.Player;
import server.model.player.PoliticCard;
import util.ColorConverter;
public class PlayerProperty {
	
	private static Map<String, String> cardsImage;
	static {
		cardsImage = new HashMap<>();
		cardsImage.put("#ffffff", "/politicCards/politicWhite.png"); //white http://i.imgur.com/3IpU6RZ.png
		cardsImage.put("#000000", "/politicCards/politicBlack.png"); //black http://i.imgur.com/fx111fM.png
		cardsImage.put("#ff9900", "/politicCards/politicOrange.png"); //orange http://i.imgur.com/RkE0x6s.png
		cardsImage.put("#0066ff", "/politicCards/politicBlue.png"); //blue http://i.imgur.com/8OjyOO1.pnq
		cardsImage.put("#ff99cc", "/politicCards/politicPink.png"); //pink http://i.imgur.com/QMetxhE.png
		cardsImage.put("#cc33ff", "/politicCards/politicPurple.png"); //purple http://i.imgur.com/jcAOwVA.png
		cardsImage.put("multi", "/politicCards/politicMulti.png"); //multi http://i.imgur.com/ySc0dOQ.png
	}
	
	private StringProperty name;
	private Color color;
	
	private IntegerProperty coins;
	private IntegerProperty assistants;
	private IntegerProperty nobility;
	private IntegerProperty victory;
	private BooleanProperty canNotDoMainAction;
	private BooleanProperty canNotDoSideAction;
	private BooleanProperty isNotMyTurn;
	/**
	 * web color repreentation of each politic card color
	 */
	private ObservableList<String> politicCards;
	private ObservableList<PermissionProperty> permissions;
	
	/**
	 * Create a new empty PlayerProperty
	 */
	public PlayerProperty() {
		canNotDoMainAction = new SimpleBooleanProperty(true);
		canNotDoSideAction = new SimpleBooleanProperty(true);
		isNotMyTurn = new SimpleBooleanProperty(true);
		name = new SimpleStringProperty("");
		coins = new SimpleIntegerProperty(0);
		assistants = new SimpleIntegerProperty(0);
		nobility = new SimpleIntegerProperty(0);
		victory = new SimpleIntegerProperty(0);
		politicCards = FXCollections.observableArrayList();
		permissions = FXCollections.observableArrayList();	
	}
	
	/**
	 * set the player's color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * @return the player's color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * set all the player's parameters except for the permission cards. 
	 * This is only useful in the initialization
	 * @param player the player server class
	 * @return the setted PlayerProperty
	 */
	public PlayerProperty setAllButPermissions(Player player) {
		canNotDoMainAction.set(player.getMainActionsLeft()>0? false:true);
		canNotDoSideAction.set(player.getIfExtraActionDone());
		name.set(player.getName());
		assistants.set(player.getAssistants().getAmount());
		victory.set(player.getVictoryPoints().getAmount());
		coins.set(player.getCoins().getAmount());
		nobility.set(player.getNobility().getAmount());
		
		politicCards.clear();
		for(PoliticCard card: player.getPoliticCard()) {
			if(!card.isMultipleColor()) {
				politicCards.add(ColorConverter.awtToWeb(card.getCardColor()));
			} else {
				politicCards.add("multi");
			}
		}	
		return this;
	}

	/**
	 * Set every parameters of the playerProperty
	 * @param player the given player from the server
	 */
	public void setAll(Player player) {
		this.setAllButPermissions(player);
		this.setAllPermissions(player);
		
	}
	
	/**
	 * @return the name of the client as a property
	 */
	public StringProperty nameProperty() {
		return name;
	}

	/**
	 * @return a property with true if the player can not do any more main action,
	 * false otherwise
	 */
	public BooleanProperty canNotDoMainAction() {
		return this.canNotDoMainAction;
	}
	/**
	 * @return a property with true if the player can not do any more side action,
	 * false otherwise
	 */
	public BooleanProperty canNotDoSideAction() {
		return this.canNotDoSideAction;
	}
	
	/**
	 * @return a property with true if is not the player's turn,
	 * false otherwise
	 */
	public BooleanProperty isNotMyTurn() {
		return this.isNotMyTurn;
	}
	
	/**
	 * @return a property of the player's coins
	 */
	public IntegerProperty coinsProperty() {
		return coins;
	}
	
	/**
	 * @return a property of the player's assistants
	 */
	public IntegerProperty assistantsProperty() {
		return assistants;
	}
	
	/**
	 * @return a property of the player's victory points
	 */
	public IntegerProperty victoryProperty() {
		return victory;
	}
	
	/**
	 * @return a property of the player's nobility points
	 */
	public IntegerProperty nobilityProperty() {
		return nobility;
	}
	
	/**
	 * @return a list of string representing the politic cards colors
	 */
	public ObservableList<String> getPoliticCards() {
		return this.politicCards;
	}
	
	/**
	 * @return the map for politic cards images
	 */
	public static Map<String, String> getPoliticCardsImages() {
		return cardsImage;
	}
	
	/**
	 * @return a list of the player's permission
	 */
	public ObservableList<PermissionProperty> getPermissions() {
		return this.permissions;
	}
	
	/**
	 * set All the player's permission
	 * @param player the player's data where unwrap the permissions
	 */
	public void setAllPermissions(Player player) {
		permissions.clear();
		for(PermissionCard pc: player.getPermissionCard()) {
			addPermission(pc);
		}
	}
	
	/**
	 * add a permission to the player
	 * @param p the card to unwrap and add
	 */
	public void addPermission(PermissionCard p) {
		PermissionProperty card = new PermissionProperty();
		card.set(p);
		permissions.add(card);
	}
}
